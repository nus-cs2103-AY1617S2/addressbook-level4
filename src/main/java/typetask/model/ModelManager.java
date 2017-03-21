package typetask.model;

import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import typetask.commons.core.ComponentManager;
import typetask.commons.core.LogsCenter;
import typetask.commons.core.UnmodifiableObservableList;
import typetask.commons.events.model.TaskManagerChangedEvent;
import typetask.commons.util.CollectionUtil;
import typetask.commons.util.StorageUtil;
import typetask.commons.util.StringUtil;
import typetask.model.task.ReadOnlyTask;
import typetask.model.task.Task;
import typetask.model.task.TaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private Stack<TaskManager> taskManagerHistory = new Stack<TaskManager>();
    private Stack<TaskManager> redoTaskManagerHistory = new Stack<TaskManager>();

    public static final Integer STATUS_EMPTY_HISTORY = 0;
    public static final Integer STATUS_AVAILABLE_HISTORY = 1;
    public static final Integer STATUS_ERROR_HISTORY = -1;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with Task Manager: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getIncompleteList());
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask) {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
    }

    //@@author A0144902L
    @Override
    public synchronized void completeTask(int index, ReadOnlyTask target) throws TaskNotFoundException {
        assert target != null;
        taskManager.completeTask(target);
        indicateTaskManagerChanged();
    }

    //@@author A0139926R
    /** Stores current TaskManager state */
    @Override
    public synchronized void storeTaskManager(String command) {

        StorageUtil.storeConfig(null);
        taskManagerHistory.push(new TaskManager(taskManager));
        redoTaskManagerHistory.clear();
        StorageUtil.clearRedoConfig();
    }

    /** Reverts changes made from restoring recently saved TaskManager state */
    @Override
    public synchronized int revertTaskManager() {
        if (StorageUtil.isRedoConfigHistoryEmpty() && redoTaskManagerHistory.isEmpty()) {
            return STATUS_EMPTY_HISTORY;
        } else if (!redoTaskManagerHistory.isEmpty() && redoTaskManagerHistory.peek() == null) {
            redoTaskManagerHistory.pop();
            taskManagerHistory.push(null);
            return STATUS_AVAILABLE_HISTORY;
        } else {
            TaskManager redoTaskManager = redoTaskManagerHistory.pop();
            taskManagerHistory.push(new TaskManager(taskManager));
            this.resetData(redoTaskManager);
            return STATUS_AVAILABLE_HISTORY;
        }
    }
    /** Restores recently saved TaskManager state */
    @Override
    public synchronized int restoreTaskManager() {
        if (StorageUtil.isConfigHistoryEmpty() && taskManagerHistory.isEmpty()) {
            return STATUS_EMPTY_HISTORY;
        } else if (!taskManagerHistory.isEmpty() && taskManagerHistory.peek() == null) {
            taskManagerHistory.pop();
            redoTaskManagerHistory.push(null);
            return STATUS_AVAILABLE_HISTORY;
        } else {
            TaskManager recentTaskManager = taskManagerHistory.pop();
            redoTaskManagerHistory.push(new TaskManager(taskManager));
            this.resetData(recentTaskManager);
            return STATUS_AVAILABLE_HISTORY;
        }

    }
    @Override
    public synchronized void rollBackTaskManager(boolean isStorageOperation) {

        taskManagerHistory.pop();
        if (isStorageOperation) {
            StorageUtil.undoConfig();
        }
    }
    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
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
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
