package seedu.address.model;

import java.util.Date;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
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

    private static final Logger logger = LogsCenter
            .getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    private Stack<String> commandHistory;
    private Stack<TaskManager> taskHistory;
    private Stack<Predicate> predicateHistory;
    private Stack<String> redoCommandHistory;

    private static final String MESSAGE_ON_DELETE = "Task deleted";
    private static final String MESSAGE_ON_ADD = "Task added";
    private static final String MESSAGE_ON_RESET = "Task list loaded";
    private static final String MESSAGE_ON_UPDATE = "Task updated";
    private static final String MESSAGE_ON_SAVETO = "Save location changed to ";

    // TODO change message to fit updateFilteredTaskList's use cases
    private static final String MESSAGE_ON_UPDATELIST = "[Debug] Update FilteredTaskList";
    private static final String MESSAGE_ON_UNDO = "Undo completed";

    private final HashMap<String, Integer> indexMap;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager
                + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        this.indexMap = new HashMap<String, Integer>();
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
        commandHistory = new Stack<String>();
        taskHistory = new Stack<TaskManager>();
        predicateHistory = new Stack<Predicate>();
        redoCommandHistory = new Stack<String>();
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateTaskManagerChanged(MESSAGE_ON_RESET);
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged(String message) {
        raise(new TaskManagerChangedEvent(taskManager, message));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target)
            throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged(MESSAGE_ON_DELETE);
    }

    @Override
    public synchronized void addTask(Task task)
            throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged(MESSAGE_ON_ADD);
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws DuplicateTaskException {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks
                .getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged(MESSAGE_ON_UPDATE);
    }

    @Override
    public void updateSaveLocation(String path) {
        indicateTaskManagerChanged(MESSAGE_ON_SAVETO + path);
    }

    @Override
    public void saveCurrentState(String commandText) {
        TaskManager copiedTaskManager = new TaskManager(taskManager);
        taskHistory.add(copiedTaskManager);
        predicateHistory.add(filteredTasks.getPredicate());
        commandHistory.add(commandText);
    }


    @Override
    public String undoLastCommand() throws NoPreviousCommandException {
        assert commandHistory.size() == taskHistory.size() && taskHistory.size() == predicateHistory.size();

        if (commandHistory.isEmpty()) {
            throw new NoPreviousCommandException("No previous commands were found.");
        }

        // Get previous command, taskManager and view
        String toUndo = commandHistory.pop();
        taskManager.resetData(taskHistory.pop());
        filteredTasks.setPredicate(predicateHistory.pop());

        // Store it in case of redo
        redoCommandHistory.add(toUndo);

        indicateTaskManagerChanged(MESSAGE_ON_UNDO);
        return toUndo;
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
        indicateTaskManagerChanged(MESSAGE_ON_UPDATELIST);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords, Date date,
            Set<String> tagKeys) {
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

    public Predicate<ReadOnlyTask> isTitleContainsKeyword(
            Set<String> keywords) {
        assert !keywords
                .isEmpty() : "no keywords provided for a keyword search";
        return t -> {
            return keywords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(
                            t.getName().fullName, keyword))
                    .findAny().isPresent();
        };
    }

    public Predicate<ReadOnlyTask> isTagsContainKeyword(Set<String> keywords) {
        assert !keywords.isEmpty() : "no keywords provided for a tag search";
        return t -> {
            return keywords.stream().filter(keyword -> {
                boolean f = false;
                for (Tag tag : t.getTags()) {
                    f = f || StringUtil.containsWordIgnoreCase(tag.getTagName(),
                            keyword);
                }
                return f;
            }).findAny().isPresent();
        };
    }

    @Override
    public void prepareTaskList(ObservableList<ReadOnlyTask> taskListToday,
            ObservableList<ReadOnlyTask> taskListFuture,
            ObservableList<ReadOnlyTask> taskListCompleted) {
        ObservableList<ReadOnlyTask> taskList = getFilteredTaskList();
        taskListToday.clear();
        taskListFuture.clear();
        taskListCompleted.clear();
        // TODO potential performance bottleneck here
        indexMap.clear();
        // initialise displayed index
        int todayID = 1;
        int futureID = 1;
        int completedID = 1;
        ListIterator<ReadOnlyTask> iter = taskList.listIterator();
        while (iter.hasNext()) {
            ReadOnlyTask tmpTask = iter.next();
            // set task id to be displayed, the id here is 1-based
            if (tmpTask.isToday() && !tmpTask.isDone()) {
                tmpTask.setID("T" + todayID);
                taskListToday.add(tmpTask);
                indexMap.put("T" + todayID, iter.nextIndex() - 1);
                todayID++;
            } else if (!tmpTask.isDone()) {
                tmpTask.setID("F" + futureID);
                taskListFuture.add(tmpTask);
                indexMap.put("F" + futureID, iter.nextIndex() - 1);
                futureID++;
            } else {
                tmpTask.setID("C" + completedID);
                taskListCompleted.add(tmpTask);
                indexMap.put("C" + completedID, iter.nextIndex() - 1);
                completedID++;
            }
        }
        printIndexMap(indexMap);
    }

    // For debugging
    public void printIndexMap(HashMap<String, Integer> map) {
        logger.info("=============indexmap content==============");
        for (String name: map.keySet()) {
            String key = name.toString();
            String value = map.get(name).toString();
            logger.info(key + " " + value);
        }
    }

    @Override
    public int parseUIIndex(String uiIndex) {
        logger.info(">>>>>>>>>>>>query UI index:" + uiIndex);
        logger.info(">>>>>>>>>>>>Absolute index:" + (indexMap.get(uiIndex) + 1));
        assert uiIndex != null;
        assert indexMap.containsKey(uiIndex);
        // plus one since all current commands take index as 1-based
        return indexMap.get(uiIndex) + 1;
    }

    @Override
    public boolean isValidUIIndex(String uiIndex) {
        return indexMap.containsKey(uiIndex);
    }

    public Predicate<ReadOnlyTask> isDueOnThisDate(Date date) {
        assert date != null : "no date provided for a deadline search";
        return t -> t.getDeadline().isSameDay(date);
    }
}
