package seedu.task.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.History;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.model.FilePathChangedEvent;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.commons.util.StringUtil;
import seedu.task.commons.util.TaskUtil;
import seedu.task.model.task.Date;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of KIT data. All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final History history;
    private UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     *
     * @throws IllegalValueException
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());

        this.userPrefs = userPrefs;
        this.history = History.getInstance();
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    // @@author A0140063X
    /**
     * Resets data of taskManager.
     */
    @Override
    public void resetData(ReadOnlyTaskManager newData) throws IllegalValueException {
        taskManager.resetData(newData);
        indicateTaskManagerChanged(history.getBackupFilePath());
    }

    // @@author A0140063X
    /**
     * Load data into taskManager. Used by Undo/Redo Command.
     */
    @Override
    public void undoData(ReadOnlyTaskManager newData) throws IllegalValueException {
        taskManager.resetData(newData);
        raise(new TaskManagerChangedEvent(taskManager, history.getBackupFilePath()));
    }

    // @@author
    /**
     * Load data into taskManager. Used by Load Command.
     */
    @Override
    public void loadDataWithoutSaving(ReadOnlyTaskManager newData) throws IllegalValueException {
        taskManager.resetData(newData);
    }

    // @@author A0140063X
    /**
     *
     * @return taskManager of model.
     */
    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    // @@author A0140063X
    /**
     * Raises an event to indicate the model has changed.
     *
     * @param backupFilePath
     *            File path to back up into.
     */
    private void indicateTaskManagerChanged(String backupFilePath) {
        history.handleTaskManagerChanged(backupFilePath);
        raise(new TaskManagerChangedEvent(taskManager, backupFilePath));
    }

    // @@author
    /** Raises an event to indicate the file path has changed */
    private void indicateFilePathChanged(String newPath) {
        raise(new FilePathChangedEvent(newPath, taskManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged(history.getBackupFilePath());
    }

    // @@author A0139975J
    @Override
    public synchronized void isDoneTask(int index) throws TaskNotFoundException {
        int taskManagerIndex = filteredTasks.getSourceIndex(index);
        taskManager.updateDone(taskManagerIndex);
        indicateTaskManagerChanged(history.getBackupFilePath());
    }

    // @@author A0139975J
    @Override
    public synchronized void unDoneTask(int index) throws TaskNotFoundException {
        int taskManagerIndex = filteredTasks.getSourceIndex(index);
        taskManager.updateUnDone(taskManagerIndex);
        indicateTaskManagerChanged(history.getBackupFilePath());
    }

    // @@author A0140063X
    /**
     *
     * @param target
     *            Target task to change.
     * @param eventId
     *            Event id to change into.
     */
    @Override
    public void setTaskEventId(ReadOnlyTask target, String eventId)
            throws TaskNotFoundException, IllegalValueException {
        taskManager.setTaskEventId(target, eventId);
        indicateTaskManagerChanged("");
    }

    // @@author
    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTaskToFront(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged(history.getBackupFilePath());
    }

    // @@author A0140063X
    /**
     * This method adds every task in tasks to model.
     *
     * @param tasks
     *            ArrayList of task to add.
     */
    @Override
    public void addMultipleTasks(ArrayList<Task> tasks) {
        for (Task task : tasks) {
            try {
                taskManager.addTaskToFront(task);
            } catch (DuplicateTaskException e) {
                logger.info("Duplicate task " + task.getName() + " not added.");
            }
        }

        updateFilteredListToShowAll();
        indicateTaskManagerChanged(history.getBackupFilePath());
    }

    // @@author
    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask) throws IllegalValueException {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged(history.getBackupFilePath());
    }

    @Override
    public void sortTaskList() {
        taskManager.sortTaskList();
        indicateTaskManagerChanged("");
    }

    //@@author A0142939W
    @Override
    public void changeFilePath(String newPath) {
        indicateFilePathChanged(newPath);
    }

    //@@author A0142939W
    @Override
    public void loadFromLocation(String loadPath) {
        indicateFilePathChanged(loadPath);
    }
    //@@author

    // =========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }
    //@@author A0139975J
    @Override
    public void updateFilteredTaskListFloat() {
        updateFilteredTaskList(new PredicateExpression(new FloatDateQualifier()));
    }
    //@@author
    @Override
    public void updateFilteredTaskList(Set<String> keywords, boolean isExact) {
        updateFilteredTaskList(new PredicateExpression(new StringQualifier(keywords, isExact)));
    }

    @Override
    public void updateFilteredTaskList(String keyword) {
        updateFilteredTaskList(new PredicateExpression(new TagQualifier(keyword)));
    }

    //@@author A0139975J-reused
    @Override
    public void updateFilteredTaskList(boolean value) {
        updateFilteredTaskList(new PredicateExpression(new DoneQualifier(value)));
    }
    //@@author
    @Override
    public void updateFilteredTaskList(Set<String> keywords, Date date, boolean isexact) {
        // TODO Auto-generated method stub
        updateFilteredTaskList(new PredicateExpression(new StringAndDateQualifier(keywords, date)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    public UserPrefs getUserPrefs() {
        return userPrefs;
    }

    // ========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);

        @Override
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);

        @Override
        String toString();
    }

    // @@author A0142487Y
    /**
     * This qualifier is specifically for strings,including name, location,remark and tags.
     *
     * @author Xu
     *
     */
    private class StringQualifier implements Qualifier {
        private boolean isExact = false;
        private boolean isPossibleDate = false;
        private Set<String> keywords;

        StringQualifier(Set<String> keywords, boolean isExact) {
            this.isExact = isExact;
            this.keywords = keywords;
        }

        StringQualifier(Set<String> keywords, boolean isExact, boolean isPossibleDate) {
            this.isExact = isExact;
            this.keywords = keywords;
            this.isPossibleDate = isPossibleDate;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (isExact) {
                return TaskUtil.doesTaskContainExactKeywords(task, keywords);
            } else if (isPossibleDate) {
                return TaskUtil.doesTaskContainExactKeyword(task, keywords);
            } else {
                for (String keyword : keywords) {
                    if (!TaskUtil.doesTaskContainKeyword(task, keyword)) {
                        return false;
                    }
                }
                return true;
            }
        }

        @Override
        public String toString() {
            return "Target to be searched for =" + String.join(", ", keywords);
        }
    }

    private class StringAndDateQualifier implements Qualifier {
        private boolean isExact = false;
        private boolean isPossibleDate = true;
        private Set<String> keywords;
        private Date date;
        private StringQualifier stringQualifier;
        private StringQualifier dateAsStringQualifier; // e.g. to search "tomorrow" as a string instead of a date
        private DateQualifier dateQualifier;

        StringAndDateQualifier(Set<String> keywords, Date date) {
            assert keywords != null;
            assert date != null;
            this.date = date;
            this.keywords = keywords;
            this.dateQualifier = new DateQualifier(this.date);
            this.stringQualifier = new StringQualifier(this.keywords, isExact);

            // this particular qualifier parses the keywords used to formulate a date as separate strings and search for
            // those strings
            this.dateAsStringQualifier = new StringQualifier(
                    new HashSet<>(StringUtil.asListWithoutEmptyString(this.date.getExtractedFrom())), isExact,
                    isPossibleDate);
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return this.dateQualifier.date.isNull() ? this.stringQualifier.run(task)
                    : (this.stringQualifier.run(task)
                            && (this.dateQualifier.run(task) || this.dateAsStringQualifier.run(task)));
        }
    }
    // @@author

    // @@author A0142487Y-reused
    private class TagQualifier implements Qualifier {

        private String tagKeyword;

        TagQualifier(String tagKeyWord) {
            this.tagKeyword = tagKeyWord;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return CollectionUtil.doesAnyStringMatch(task.getTags().getGenericCollection(), tagKeyword);
        }

    }
    // @@author

    // @@author A0139975J
    private class DateQualifier implements Qualifier {

        private Date date;

        // @@author A0139975J
        DateQualifier(Date date) {
            this.date = date;
        }

        // @@author A0139975J
        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getEndDate().equalsIgnoreTime(date) || task.getStartDate().equalsIgnoreTime(date);
        }
    }

    // @@author A0139975J
    private class FloatDateQualifier implements Qualifier {

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getEndDate().isNull() && task.getStartDate().isNull();
        }
    }

    // @@author A0139975J
    private class DoneQualifier implements Qualifier {

        private boolean value;

        // @@author A0139975J
        DoneQualifier(boolean value) {
            this.value = value;
        }

        // @@author A0139975J
        @Override
        public boolean run(ReadOnlyTask task) {
            return this.value == task.isDone();
        }
    }

}
