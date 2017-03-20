package seedu.address.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.ToDoAppChangedEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniqueTaskList;
import seedu.address.model.person.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ToDoApp toDoApp;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    /**
     * Initializes a ModelManager with the given toDoApp and userPrefs.
     */
    public ModelManager(ReadOnlyToDoApp toDoApp, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(toDoApp, userPrefs);

        logger.fine("Initializing with ToDoApp: " + toDoApp + " and user prefs " + userPrefs);

        this.toDoApp = new ToDoApp(toDoApp);
        filteredTasks = new FilteredList<>(this.toDoApp.getTaskList());
    }

    public ModelManager() {
        this(new ToDoApp(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyToDoApp newData) {
        toDoApp.resetData(newData);
        indicateToDoAppChanged();
    }

    @Override
    public ReadOnlyToDoApp getToDoApp() {
        return toDoApp;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateToDoAppChanged() {
        raise(new ToDoAppChangedEvent(toDoApp));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        toDoApp.removeTask(target);
        indicateToDoAppChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        toDoApp.addTask(task);
        updateFilteredListToShowAll();
        indicateToDoAppChanged();
    }

    //@@author A0114395E
    @Override
    public synchronized void addTask(Task task, int idx) throws UniqueTaskList.DuplicateTaskException {
        toDoApp.addTask(task, idx);
        updateFilteredListToShowAll();
        indicateToDoAppChanged();
    }
    //@@author

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int toDoAppIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        toDoApp.updateTask(toDoAppIndex, editedTask);
        indicateToDoAppChanged();
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
