package seedu.address.model;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.model.TaskManagerExportEvent;
import seedu.address.commons.events.model.TaskManagerImportEvent;
import seedu.address.commons.events.model.TaskManagerPathChangedEvent;
import seedu.address.commons.events.model.TaskManagerUseNewPathEvent;
import seedu.address.commons.events.storage.ImportEvent;
import seedu.address.commons.events.storage.ReadFromNewFileEvent;
import seedu.address.commons.events.ui.ShowCompletedTaskEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.SaveToCommand;
import seedu.address.logic.commands.UseThisCommand;
import seedu.address.model.exceptions.NoPreviousCommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task manager data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    private Stack<String> commandHistory;
    private Stack<TaskManager> taskHistory;
    private Stack<Predicate<? super ReadOnlyTask>> predicateHistory;
    private Stack<String> redoCommandHistory;
    private Stack<Boolean> completedViewHistory;

    private static final String MESSAGE_ON_DELETE = "Task deleted";
    private static final String MESSAGE_ON_ADD = "Task added";
    private static final String MESSAGE_ON_RESET = "Task list loaded";
    private static final String MESSAGE_ON_UPDATE = "Task updated";
    private static final String MESSAGE_ON_SAVETO = "Save location changed to ";
    private static final String MESSAGE_ON_EXPORT = "Data to be exported to ";
    private static final String MESSAGE_ON_USETHIS = "Reading data from ";
    private static final String MESSAGE_ON_IMPORT = "Importing data from ";

    // TODO change message to fit updateFilteredTaskList's use cases
    private static final String MESSAGE_ON_UPDATELIST = "[Debug] Update FilteredTaskList";
    private static final String MESSAGE_ON_UNDO = "Undo completed";

    private final HashMap<String, Integer> indexMap;
    private boolean completedViewOpen;

    /**
     * Compares two ReadOnlyTask by deadline. Tasks without deadline will be
     * deemed as the smallest. If both tasks have deadline, the result will be
     * the difference in their UNIX time.
     */
    private static final Comparator<? super ReadOnlyTask> TaskDatetimeComparator = new Comparator<ReadOnlyTask>() {
        @Override
        public int compare(ReadOnlyTask o1, ReadOnlyTask o2) {
            return o1.compareTo(o2);
        }
    };

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        this.indexMap = new HashMap<String, Integer>();
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
        commandHistory = new Stack<String>();
        taskHistory = new Stack<TaskManager>();
        predicateHistory = new Stack<Predicate<? super ReadOnlyTask>>();
        redoCommandHistory = new Stack<String>();
        completedViewHistory = new Stack<Boolean>();
        completedViewOpen = false;
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void showCompletedTaskList() {
        completedViewOpen = true;
        raise(new ShowCompletedTaskEvent(ShowCompletedTaskEvent.Action.SHOW));
    }

    @Override
    public void hideCompletedTaskList() {
        completedViewOpen = false;
        raise(new ShowCompletedTaskEvent(ShowCompletedTaskEvent.Action.HIDE));
    }

    @Override
    public void setData(ReadOnlyTaskManager newData, Boolean clearPrevTasks) {
        taskManager.setData(newData, clearPrevTasks);
        indicateTaskManagerChanged(MESSAGE_ON_RESET);
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged(String message) {
        logger.fine(message);
        raise(new TaskManagerChangedEvent(taskManager));
    }

    /** Raises an event to indicate the path needs to be changed */
    private void indicateTaskManagerPathChanged(String message, String path) {
        logger.fine(message);
        raise(new TaskManagerPathChangedEvent(taskManager, path));
    }

    /** Raises an event to indicate the path needs to be changed */
    private void indicateTaskManagerExport(String message, String path) {
        logger.fine(message);
        raise(new TaskManagerExportEvent(taskManager, path));
    }

    /**
     * Raises an event to indicate that the task manager should import data from
     * specified path
     */
    private void indicateTaskManagerImport(String message, String path) {
        logger.fine(message);
        raise(new TaskManagerImportEvent(path));
    }

    /**
     * Raises an event to indicate that the task manager should read from a
     * different path
     */
    private void indicateTaskManagerUseNewPath(String message, String path) {
        logger.fine(message);
        raise(new TaskManagerUseNewPathEvent(path));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged(MESSAGE_ON_DELETE);
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged(MESSAGE_ON_ADD);
    }

    @Override
    public void updateTask(int filteredTaskListIndex, Task editedTask) throws DuplicateTaskException {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged(MESSAGE_ON_UPDATE);
    }

    @Override
    public void updateSaveLocation(String path) {
        assert path != null;
        indicateTaskManagerPathChanged(MESSAGE_ON_SAVETO + path, path);
    }

    @Override
    public void exportToLocation(String path) {
        assert path != null;
        indicateTaskManagerExport(MESSAGE_ON_EXPORT + path, path);
    }

    @Override
    public void importFromLocation(String path) {
        assert path != null;
        indicateTaskManagerImport(MESSAGE_ON_IMPORT + path, path);
    }

    @Override
    public void useNewSaveLocation(String path) {
        assert path != null;
        indicateTaskManagerUseNewPath(MESSAGE_ON_USETHIS + path, path);
    }

    @Override
    public void saveCurrentState(String commandText) {
        TaskManager copiedTaskManager = new TaskManager(taskManager);
        taskHistory.add(copiedTaskManager);
        Predicate<? super ReadOnlyTask> predicate = filteredTasks.getPredicate();
        predicateHistory.add(predicate);
        commandHistory.add(commandText);
        completedViewHistory.add(completedViewOpen);
    }

    @Override
    public void discardCurrentState() {
        assert commandHistory.size() == taskHistory.size() && taskHistory.size() == predicateHistory.size()
                && predicateHistory.size() == completedViewHistory.size();
        if (!commandHistory.isEmpty()) {
            commandHistory.pop();
            taskHistory.pop();
            predicateHistory.pop();
            completedViewHistory.pop();
        }
    }

    @Override
    public String undoLastCommand() throws NoPreviousCommandException {
        assert commandHistory.size() == taskHistory.size() && taskHistory.size() == predicateHistory.size()
                && predicateHistory.size() == completedViewHistory.size();

        if (commandHistory.isEmpty()) {
            throw new NoPreviousCommandException("No previous commands were found.");
        }

        // Get previous command, taskManager and view
        String toUndo = commandHistory.pop();
        taskManager.setData(taskHistory.pop(), true);
        filteredTasks.setPredicate(predicateHistory.pop());

        // Set completed tasks view
        if (completedViewHistory.pop()) {
            showCompletedTaskList();
        } else {
            hideCompletedTaskList();
        }

        // Store command in case of redo
        redoCommandHistory.add(toUndo);

        if (toUndo.startsWith(SaveToCommand.COMMAND_WORD)) {
            indicateTaskManagerPathChanged(MESSAGE_ON_UNDO, null);
        } else if (toUndo.startsWith(UseThisCommand.COMMAND_WORD)) {
            indicateTaskManagerUseNewPath(MESSAGE_ON_UNDO, null);
        } else if (toUndo.startsWith(ExportCommand.COMMAND_WORD)) {
            indicateTaskManagerExport(MESSAGE_ON_UNDO, null);
        } else {
            indicateTaskManagerChanged(MESSAGE_ON_UNDO);
        }

        return toUndo;
    }

    @Override
    public String getRedoCommand() throws NoPreviousCommandException {

        if (redoCommandHistory.isEmpty()) {
            throw new NoPreviousCommandException("No previous commands were found.");
        }

        return redoCommandHistory.pop();
    }

    @Override
    public void clearRedoCommandHistory() {
        redoCommandHistory.clear();
    }

    @Override
    @Subscribe
    public void handleReadFromNewFileEvent(ReadFromNewFileEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "New file data loaded into model"));
        setData(event.data, true);
    }

    @Override
    @Subscribe
    public void handleImportEvent(ImportEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Add file data into model"));
        setData(event.data, false);
    }

    // =========== Filtered Task List Accessors
    // =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords, Date date, Set<String> tagKeys) {
        Predicate<ReadOnlyTask> predicate = t -> false;
        if (keywords != null) {
            predicate = predicate.or(isTitleContainsKeyword(keywords));
        }
        if (date != null) {
            predicate = predicate.or(isDueOnThisDate(date));
        }
        if (tagKeys != null) {
            predicate = predicate.or(isTagsContainKeyword(tagKeys));
        }
        filteredTasks.setPredicate(predicate);
        indicateTaskManagerChanged(MESSAGE_ON_UPDATELIST);
    }

    // ========== Inner classes/interfaces used for filtering
    // =================================================

    public Predicate<ReadOnlyTask> isTitleContainsKeyword(Set<String> keywords) {
        assert !keywords.isEmpty() : "no keywords provided for a keyword search";
        return t -> {
            return keywords.stream().filter(keyword -> StringUtil.containsWordIgnoreCase(t.getName().fullName, keyword))
                    .findAny().isPresent();
        };
    }

    public Predicate<ReadOnlyTask> isTagsContainKeyword(Set<String> keywords) {
        assert !keywords.isEmpty() : "no keywords provided for a tag search";
        return t -> {
            return keywords.stream().filter(keyword -> {
                boolean f = false;
                for (Tag tag : t.getTags()) {
                    f = f || StringUtil.containsWordIgnoreCase(tag.getTagName(), keyword);
                }
                return f;
            }).findAny().isPresent();
        };
    }

    @Override
    public void prepareTaskList(ObservableList<ReadOnlyTask> taskListToday, ObservableList<ReadOnlyTask> taskListFuture,
            ObservableList<ReadOnlyTask> taskListCompleted) {
        splitTaskList(taskListToday, taskListFuture, taskListCompleted);
        sortTaskList(taskListToday, taskListFuture, taskListCompleted);
        assignUiIndex(taskListToday, taskListFuture, taskListCompleted);
    }

    private void splitTaskList(ObservableList<ReadOnlyTask> taskListToday, ObservableList<ReadOnlyTask> taskListFuture,
            ObservableList<ReadOnlyTask> taskListCompleted) {
        ObservableList<ReadOnlyTask> taskList = getFilteredTaskList();
        taskListToday.clear();
        taskListFuture.clear();
        taskListCompleted.clear();
        ListIterator<ReadOnlyTask> iter = taskList.listIterator();
        while (iter.hasNext()) {
            ReadOnlyTask tmpTask = iter.next();
            // set task id to be displayed, the id here is 1-based
            if (tmpTask.isToday() && !tmpTask.isDone()) {
                // absolute index here will be replace to relative index in
                // assignUiIndex method
                tmpTask.setID("" + (iter.nextIndex() - 1));
                taskListToday.add(tmpTask);
            } else if (!tmpTask.isDone()) {
                tmpTask.setID("" + (iter.nextIndex() - 1));
                taskListFuture.add(tmpTask);
            } else {
                tmpTask.setID("" + (iter.nextIndex() - 1));
                taskListCompleted.add(tmpTask);
            }
        }
    }

    private void sortTaskList(ObservableList<ReadOnlyTask> taskListToday, ObservableList<ReadOnlyTask> taskListFuture,
            ObservableList<ReadOnlyTask> taskListCompleted) {
        taskListToday.sort(TaskDatetimeComparator);
        taskListFuture.sort(TaskDatetimeComparator);
        taskListCompleted.sort(TaskDatetimeComparator);
    }

    private void assignUiIndex(ObservableList<ReadOnlyTask> taskListToday, ObservableList<ReadOnlyTask> taskListFuture,
            ObservableList<ReadOnlyTask> taskListCompleted) {
        // TODO potential performance bottleneck here
        indexMap.clear();
        // initialise displayed index
        int todayID = 1;
        int futureID = 1;
        int completedID = 1;
        ListIterator<ReadOnlyTask> iterToday = taskListToday.listIterator();
        ListIterator<ReadOnlyTask> iterFuture = taskListFuture.listIterator();
        ListIterator<ReadOnlyTask> iterCompleted = taskListCompleted.listIterator();
        while (iterToday.hasNext()) {
            ReadOnlyTask tmpTask = iterToday.next();
            indexMap.put("T" + todayID, Integer.valueOf(tmpTask.getID()));
            tmpTask.setID("T" + todayID);
            todayID++;
            logger.info(tmpTask.getAsText() + ">>> Assign ID:" + tmpTask.getID());
        }
        while (iterFuture.hasNext()) {
            ReadOnlyTask tmpTask = iterFuture.next();
            indexMap.put("F" + futureID, Integer.valueOf(tmpTask.getID()));
            tmpTask.setID("F" + futureID);
            futureID++;
            logger.info(tmpTask.getAsText() + ">>> Assign ID:" + tmpTask.getID());
        }
        while (iterCompleted.hasNext()) {
            ReadOnlyTask tmpTask = iterCompleted.next();
            indexMap.put("C" + completedID, Integer.valueOf(tmpTask.getID()));
            tmpTask.setID("C" + completedID);
            completedID++;
            logger.info(tmpTask.getAsText() + ">>> Assign ID:" + tmpTask.getID());
        }
    }

    // For debugging
    public void printIndexMap(HashMap<String, Integer> map) {
        logger.info("=============indexmap content==============");
        for (String name : map.keySet()) {
            String key = name.toString();
            String value = map.get(name).toString();
            logger.info(key + " " + value);
        }
    }

    @Override
    public int parseUIIndex(String uiIndex) {
        uiIndex = uiIndex.toUpperCase();
        assert isValidUIIndex(uiIndex);
        logger.info(">>>>>>>>>>>>query UI index:" + uiIndex);
        logger.info(">>>>>>>>>>>>Absolute index:" + (indexMap.get(uiIndex) + 1));
        assert uiIndex != null;
        assert indexMap.containsKey(uiIndex);
        // plus one since all current commands take index as 1-based
        return indexMap.get(uiIndex) + 1;
    }

    /*
     * gets UI index by absolute index
     */
    @Override
    public String getUIIndex(int index) {
        return filteredTasks.get(index).getID();
    }

    @Override
    public boolean isValidUIIndex(String uiIndex) {
        uiIndex = uiIndex.toUpperCase();
        return indexMap.containsKey(uiIndex);
    }

    // @@author A0093999Y
    public Predicate<ReadOnlyTask> isDueOnThisDate(Date date) {
        assert date != null : "no date provided for a deadline search";
        return t -> {
            if (t.getDeadline().isPresent()) {
                return t.getDeadline().get().isSameDay(date);
            }
            return false;
        };
    }
}
