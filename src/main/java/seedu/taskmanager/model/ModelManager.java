package seedu.taskmanager.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.taskmanager.commons.core.ComponentManager;
import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.core.UnmodifiableObservableList;
import seedu.taskmanager.commons.events.model.ProcrastiNomoreChangedEvent;
import seedu.taskmanager.commons.util.CollectionUtil;
import seedu.taskmanager.commons.util.StringUtil;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.task.UniqueTaskList;
import seedu.taskmanager.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ProcrastiNomore procrastiNomore;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    /**
     * Initializes a ModelManager with the given ProcrastiNomore and userPrefs.
     */
    public ModelManager(ReadOnlyProcrastiNomore procrastiNomore, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(procrastiNomore, userPrefs);

        logger.fine("Initializing with ProcrastiNomore: " + procrastiNomore + " and user prefs " + userPrefs);

        this.procrastiNomore = new ProcrastiNomore(procrastiNomore);
        filteredTasks = new FilteredList<>(this.procrastiNomore.getTaskList());
    }

    public ModelManager() {
        this(new ProcrastiNomore(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyProcrastiNomore newData) {
        procrastiNomore.resetData(newData);
        indicateProcrastiNomoreChanged();
    }

    @Override
    public ReadOnlyProcrastiNomore getProcrastiNomore() {
        return procrastiNomore;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateProcrastiNomoreChanged() {
        raise(new ProcrastiNomoreChangedEvent(procrastiNomore));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        procrastiNomore.removeTask(target);
        indicateProcrastiNomoreChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        procrastiNomore.addTask(task);
        updateFilteredListToShowAll();
        indicateProcrastiNomoreChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int procrastiNomoreIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        procrastiNomore.updateTask(procrastiNomoreIndex, editedTask);
        indicateProcrastiNomoreChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

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
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getTaskName().fullTaskName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "task name=" + String.join(", ", nameKeyWords);
        }
    }

}
