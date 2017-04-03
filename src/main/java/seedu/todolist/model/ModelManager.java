package seedu.todolist.model;

import static seedu.todolist.commons.core.GlobalConstants.DATE_FORMAT;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.todolist.commons.core.ComponentManager;
import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.commons.events.model.TodoListChangedEvent;
import seedu.todolist.commons.util.CollectionUtil;
import seedu.todolist.commons.util.StringUtil;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.todo.ReadOnlyTodo;
import seedu.todolist.model.todo.Todo;
import seedu.todolist.model.todo.UniqueTodoList;
import seedu.todolist.model.todo.UniqueTodoList.TodoNotFoundException;

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
    //@@author A0163786N
    @Override
    public void completeTodo(int filteredTodoListIndex, Date completeTime) {
        TodoList tempTodoList = new TodoList(todoList);
        int todoListIndex = filteredTodos.getSourceIndex(filteredTodoListIndex);
        todoList.completeTodo(todoListIndex, completeTime);
        previousTodoList = tempTodoList;
        indicateTodoListChanged();
    }
    //@@author
    //@@author A0163786N
    @Override
    public void uncompleteTodo(int filteredTodoListIndex) {
        TodoList tempTodoList = new TodoList(todoList);
        int todoListIndex = filteredTodos.getSourceIndex(filteredTodoListIndex);
        todoList.uncompleteTodo(todoListIndex);
        previousTodoList = tempTodoList;
        indicateTodoListChanged();
    }
    //@@author
    //@@author A0163786N
    @Override
    public void loadPreviousState() throws NoPreviousStateException {
        if (previousTodoList == null) {
            throw new NoPreviousStateException();
        }
        resetData(previousTodoList);
    }
    //@@author
    //=========== Filtered Todo List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTodo> getFilteredTodoList() {
        return new UnmodifiableObservableList<>(filteredTodos);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTodos.setPredicate(null);
    }
    //@@author A0163786N
    @Override
    public void updateFilteredTodoList(Set<String> keywords, Date startTime,
        Date endTime, Object completeTime, UniqueTagList tags) {
        updateFilteredTodoList(new PredicateExpression(
                new NameQualifier(keywords, startTime, endTime, completeTime, tags)));
    }
    //@@author
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

    //@@author A0163720M
    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;
        private Set<Tag> tags;
        private Set<String> tagKeyWords;
        private Date startTime;
        private Date endTime;
        private Object completeTime;

        NameQualifier(Set<String> nameKeyWords, Date startTime, Date endTime, Object completeTime, UniqueTagList tags) {
            this.nameKeyWords = nameKeyWords;
            this.startTime = startTime;
            this.endTime = endTime;
            this.completeTime = completeTime;
            this.tags = tags.toSet();

            // for simplicity sake, convert the Set<Tag> into Set<String> so that it can easily be filtered out
            // similar to filtering out by name
            this.tagKeyWords = new HashSet<String>();

            for (Tag tag:tags) {
                this.tagKeyWords.add(tag.tagName);
            }
        }
        //@@author A0163786N
        @Override
        public boolean run(ReadOnlyTodo todo) {
            return checkName(todo) && checkStartTime(todo) && checkEndTime(todo)
                    && checkCompleteTime(todo) && checkTags(todo);
        }
        //@@author A0163720M
        /**
         * Returns the tags or the name of the todo depending on which field is present
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (!nameKeyWords.isEmpty()) {
                sb.append("name=" + String.join(", ", nameKeyWords));
            }
            if (startTime != null) {
                sb.append("\nstart time=" + new SimpleDateFormat(DATE_FORMAT).format(startTime));
            }
            if (endTime != null) {
                sb.append("\nend time=" + new SimpleDateFormat(DATE_FORMAT).format(endTime));
            }
            if (completeTime != null) {
                if (completeTime instanceof Date) {
                    sb.append("\ncomplete time=" + new SimpleDateFormat(DATE_FORMAT).format(completeTime));
                } else {
                    sb.append("\ncomplete time=any");
                }
            }
            if (!tags.isEmpty()) {
                sb.append("\ntag=" + String.join(", ", tagKeyWords));
            }
            return sb.toString();
        }
        //@@author A0163786N
        /**
         * Helper function to simplify run function. Checks complete time
         * and returns true if todo should be shown in filtered list
         */
        private boolean checkCompleteTime(ReadOnlyTodo todo) {
            if (completeTime != null) {
                Date todoCompleteTime = todo.getCompleteTime();
                if (completeTime instanceof Date) {
                    if (todoCompleteTime == null || todoCompleteTime.after((Date) completeTime)) {
                        return false;
                    }
                } else if (completeTime.equals("")) {
                    if (todoCompleteTime == null) {
                        return false;
                    }
                } else if (completeTime.equals("not") && todoCompleteTime != null) {
                    return false;
                }
            }
            return true;
        }
        //@@author
        //@@author A0163786N
        /**
         * Helper function to simplify run function. Checks start time
         * and returns true if todo should be shown in filtered list
         */
        private boolean checkStartTime(ReadOnlyTodo todo) {
            if (startTime != null) {
                Date todoStartTime = todo.getStartTime();
                if (todoStartTime == null || todoStartTime.after(startTime)) {
                    return false;
                }
            }
            return true;
        }
        //@@author
        //@@author A0163786N
        /**
         * Helper function to simplify run function. Checks end time
         * and returns true if todo should be shown in filtered list
         */
        private boolean checkEndTime(ReadOnlyTodo todo) {
            if (endTime != null) {
                Date todoEndTime = todo.getEndTime();
                if (todoEndTime == null || todoEndTime.after(endTime)) {
                    return false;
                }
            }
            return true;
        }
        //@@author
        //@@author A0163786N
        /**
         * Helper function to simplify run function. Checks todo name
         * and returns true if todo should be shown in filtered list
         */
        private boolean checkName(ReadOnlyTodo todo) {
            if (!nameKeyWords.isEmpty()) {
                String name = todo.getName().fullName;
                if (!(nameKeyWords.stream()
                        .filter(keyword -> StringUtil.containsWordIgnoreCase(name, keyword))
                        .findAny()
                        .isPresent())) {
                    return false;
                }
            }
            return true;
        }
        //@@author
        //@@author A0163786N
        /**
         * Helper function to simplify run function. Checks todo tags
         * and returns true if todo should be shown in filtered list
         */
        private boolean checkTags(ReadOnlyTodo todo) {
            if (!tags.isEmpty()) {
                String todoTags = todo.getTagsAsString();
                if (!(tagKeyWords.stream()
                        .filter(keyword -> StringUtil.containsWordIgnoreCase(todoTags, keyword))
                        .findAny()
                        .isPresent())) {
                    return false;
                }
            }
            return true;
        }
        //@@author
    }
}
