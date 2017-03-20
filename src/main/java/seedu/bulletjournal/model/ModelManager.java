package seedu.bulletjournal.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.bulletjournal.commons.core.ComponentManager;
import seedu.bulletjournal.commons.core.LogsCenter;
import seedu.bulletjournal.commons.core.UnmodifiableObservableList;
import seedu.bulletjournal.commons.events.model.TodoListChangedEvent;
import seedu.bulletjournal.commons.util.CollectionUtil;
import seedu.bulletjournal.commons.util.StringUtil;
import seedu.bulletjournal.model.task.ReadOnlyTask;
import seedu.bulletjournal.model.task.Task;
import seedu.bulletjournal.model.task.UniqueTaskList;
import seedu.bulletjournal.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TodoList todoList;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyTodoList addressBook, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.todoList = new TodoList(addressBook);
        filteredTasks = new FilteredList<>(this.todoList.getTaskList());

    }

    public ModelManager() {
        this(new TodoList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTodoList newData) {
        todoList.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyTodoList getAddressBook() {
        return todoList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new TodoListChangedEvent(todoList));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        todoList.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        todoList.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    @Override
    public void updateTask(int filteredPersonListIndex, ReadOnlyTask editedPerson)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedPerson != null;

        int addressBookIndex = filteredTasks.getSourceIndex(filteredPersonListIndex);
        todoList.updateTask(addressBookIndex, editedPerson);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredPersonList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredPersonList(Set<String> keywords) {
        updateFilteredPersonList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredPersonList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //@@author A0105748B
    @Override
    public void updateMatchedTaskList(Set<String> keywords) {
        updateMatchedTaskList(new PredicateExpression(new StatusQualifier(keywords)));
    }

    private void updateMatchedTaskList(Expression expression) {
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
        boolean run(ReadOnlyTask person);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask person) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(person.getTaskName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    private class StatusQualifier implements Qualifier {
        private Set<String> statusKeyWords;

        StatusQualifier(Set<String> nameKeyWords) {
            this.statusKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return statusKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getStatus().value, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "status=" + String.join(", ", statusKeyWords);
        }
    }

}
