package seedu.taskboss.model;

import java.util.Stack;
import java.util.EmptyStackException;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.taskboss.commons.core.ComponentManager;
import seedu.taskboss.commons.core.LogsCenter;
import seedu.taskboss.commons.core.UnmodifiableObservableList;
import seedu.taskboss.commons.events.model.TaskBossChangedEvent;
import seedu.taskboss.commons.util.CollectionUtil;
import seedu.taskboss.commons.util.StringUtil;
import seedu.taskboss.model.task.ReadOnlyTask;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.UniqueTaskList;
import seedu.taskboss.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the TaskBoss data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskBoss taskBoss;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final Stack<ReadOnlyTaskBoss> taskbossHistory;

    /**
     * Initializes a ModelManager with the given TaskBoss and userPrefs.
     */
    public ModelManager(ReadOnlyTaskBoss taskBoss, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskBoss, userPrefs);

        logger.fine("Initializing with TaskBoss: " + taskBoss + " and user prefs " + userPrefs);

        this.taskBoss = new TaskBoss(taskBoss);
        filteredTasks = new FilteredList<>(this.taskBoss.getTaskList());
        taskbossHistory = new Stack<ReadOnlyTaskBoss>();
    }

    public ModelManager() {
        this(new TaskBoss(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskBoss newData) {
        taskbossHistory.push(new TaskBoss(this.taskBoss));
        taskBoss.resetData(newData);
        indicateTaskBossChanged();
    }

    @Override
    public ReadOnlyTaskBoss getTaskBoss() {
        return taskBoss;
    }
    
    @Override
    public void undoTaskboss() throws EmptyStackException {
    	taskBoss.resetData(taskbossHistory.pop());
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskBossChanged() {
        raise(new TaskBossChangedEvent(taskBoss));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskbossHistory.push(new TaskBoss(this.taskBoss));
        taskBoss.removeTask(target);
        indicateTaskBossChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskbossHistory.push(new TaskBoss(this.taskBoss));
        taskBoss.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskBossChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int taskBossIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskBoss.updateTask(taskBossIndex, editedTask);
        indicateTaskBossChanged();
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
