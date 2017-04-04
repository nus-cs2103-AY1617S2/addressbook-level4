package seedu.bulletjournal.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.bulletjournal.commons.core.ComponentManager;
import seedu.bulletjournal.commons.core.LogsCenter;
import seedu.bulletjournal.commons.core.UnmodifiableObservableList;
import seedu.bulletjournal.commons.events.model.FilePathChangedEvent;
import seedu.bulletjournal.commons.events.model.TodoListChangedEvent;
import seedu.bulletjournal.commons.util.CollectionUtil;
import seedu.bulletjournal.commons.util.StringUtil;
import seedu.bulletjournal.model.task.ReadOnlyTask;
import seedu.bulletjournal.model.task.Task;
import seedu.bulletjournal.model.task.UniqueTaskList;
import seedu.bulletjournal.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TodoList todoList;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    /**
     * Initializes a ModelManager with the given bulletJournal and userPrefs.
     */
    public ModelManager(ReadOnlyTodoList bulletJournal, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(bulletJournal, userPrefs);

        logger.fine("Initializing with bullet journal: " + bulletJournal + " and user prefs " + userPrefs);

        this.todoList = new TodoList(bulletJournal);
        filteredTasks = new FilteredList<>(this.todoList.getTodoList());
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
    public ReadOnlyTodoList getTodoList() {
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
        updateFilteredListToShowUndone();
        indicateAddressBookChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        int addressBookIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        todoList.updateTask(addressBookIndex, editedTask);
        indicateAddressBookChanged();
    }

    // =========== Filtered Task List Accessors
    // =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    // @@author A0105748B
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getUndoneTaskList() {
        String[] keywords = new String[2];
        keywords[0] = "undone";
        keywords[1] = "empty";
        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        updateMatchedTaskList(keywordSet);
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowUndone() {
        String[] keywords = new String[2];
        keywords[0] = "undone";
        keywords[1] = "empty";
        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        updateMatchedTaskList(keywordSet);
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

    // @@author A0105748B
    @Override
    public void updateMatchedTaskList(Set<String> keywords) {
        updateMatchedTaskList(new PredicateExpression(new StatusQualifier(keywords)));
    }

    private void updateMatchedTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    // ========== Inner classes/interfaces used for filtering
    // =================================================

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

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getTaskName().fullName, keyword))
                    .findAny().isPresent();
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
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(
                            task.getStatus() == null ? "empty" : task.getStatus().value, keyword))
                    .findAny().isPresent();
        }

        @Override
        public String toString() {
            return "status=" + String.join(", ", statusKeyWords);
        }
    }

    @Override
    public void changeDirectory(String filePath) {
        raise(new FilePathChangedEvent(filePath));
    }

}
