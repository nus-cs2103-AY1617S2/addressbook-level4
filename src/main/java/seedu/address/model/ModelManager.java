package seedu.address.model;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.TodoListChangedEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.todo.ReadOnlyTodo;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.UniqueTodoList;
import seedu.address.model.todo.UniqueTodoList.TodoNotFoundException;

/**
 * Represents the in-memory model of the todo list data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TodoList todoList;
    /**
     * Holds the previous state of the todo list before the most recent modifying change
     */
    private TodoList previousTodoList;
    private final FilteredList<ReadOnlyTodo> filteredTodos;

    /**
     * Initializes a ModelManager with the given todoList and userPrefs.
     */
    public ModelManager(ReadOnlyTodoList todoList, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(todoList, userPrefs);

        logger.fine("Initializing with todo list: " + todoList + " and user prefs " + userPrefs);

        this.todoList = new TodoList(todoList);
        filteredTodos = new FilteredList<>(this.todoList.getTodoList());
    }

    public ModelManager() {
        this(new TodoList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTodoList newData) {
    	previousTodoList = new TodoList(todoList);
        todoList.resetData(newData);
        indicateTodoListChanged();
    }

    @Override
    public ReadOnlyTodoList getTodoList() {
        return todoList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTodoListChanged() {
        raise(new TodoListChangedEvent(todoList));
    }

    @Override
    public synchronized void deleteTodo(ReadOnlyTodo target) throws TodoNotFoundException {
    	TodoList tempTodoList = new TodoList(todoList);
        todoList.removeTodo(target);
        previousTodoList = tempTodoList;
        indicateTodoListChanged();
    }

    @Override
    public synchronized void addTodo(Todo todo) throws UniqueTodoList.DuplicateTodoException {
    	TodoList tempTodoList = new TodoList(todoList);
        todoList.addTodo(todo);
        previousTodoList = tempTodoList;
        updateFilteredListToShowAll();
        indicateTodoListChanged();
    }

    @Override
    public void updateTodo(int filteredTodoListIndex, ReadOnlyTodo editedTodo)
            throws UniqueTodoList.DuplicateTodoException {
        assert editedTodo != null;

        int todoListIndex = filteredTodos.getSourceIndex(filteredTodoListIndex);
        TodoList tempTodoList = new TodoList(todoList);
        todoList.updateTodo(todoListIndex, editedTodo);
        previousTodoList = tempTodoList;
        indicateTodoListChanged();
    }

    @Override
    public void loadPreviousState() throws NoPreviousStateException {
    	if (previousTodoList == null) {
    		throw new NoPreviousStateException();
    	}
    	resetData(previousTodoList);
    	previousTodoList = null;
    }

    //=========== Filtered Todo List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTodo> getFilteredTodoList() {
        return new UnmodifiableObservableList<>(filteredTodos);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTodos.setPredicate(null);
    }

    @Override
    public void updateFilteredTodoList(Set<String> keywords) {
        updateFilteredTodoList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTodoList(Expression expression) {
        filteredTodos.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTodo todo);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTodo todo) {
            return qualifier.run(todo);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTodo todo);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTodo todo) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(todo.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
