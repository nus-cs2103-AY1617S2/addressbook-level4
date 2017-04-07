package seedu.task.model;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskComparable;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.TaskStatusPredicate;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task manager data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
    }

    // @@author A0163845X
    // brute force pattern matching algorithm
    public static boolean patternStringMatch(String p, String t) {
        int i = 0;
        int j = 0;
        while (i <= t.length() - p.length()) {
            if (p.substring(j, j + 1).equalsIgnoreCase(t.substring(i + j, i + j + 1))) {
                j++;
                if (j >= p.length()) {
                    return true;
                }
            } else {
                j = 0;
                i++;
            }
        }
        return false;
    }

    // public static String getPath(String path){
    // return path;
    // }

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

    // @@author A0146757R
    @Override
    public synchronized void completeTask(int index) throws TaskNotFoundException {
        taskManager.completeTask(index);
        indicateTaskManagerChanged();
    }

    // @@author
    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addJobTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
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
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    // ========== Inner classes/interfaces used for filtering
    // =================================================

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

        // @@author A0163845X
        @Override
        public boolean run(ReadOnlyTask task) {
            for (String s : nameKeyWords) {
                assert s.length() > 0;
                if (patternStringMatch(s, task.getTaskName().fullTaskName)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    // @@author A0163845X
    @Override
    public void undo() throws Exception {
        taskManager.undo();
        indicateTaskManagerChanged();
    }

    // @@author A0163845X
    @Override
    public void updateBackup() throws DuplicateTaskException {
        taskManager.updateBackup();
    }

    // @@author A0163845X
    @Override
    public void sort(TaskComparable t) {
        taskManager.sort(t);
    }
    // @@author A0163845X

    @Override
    public void setTaskManager(Optional<ReadOnlyTaskManager> readTaskManager) {
        if (readTaskManager.isPresent()) {
            taskManager.loadNewTaskList(readTaskManager);
        }
    }

    // @@author A0163845X
    public void redo() throws Exception {
        taskManager.redo();
        indicateTaskManagerChanged();
    }
    // @@author A0163845X

    public void filterStatus(String status) {
        filteredTasks.setPredicate(new TaskStatusPredicate(status));
    }
}
