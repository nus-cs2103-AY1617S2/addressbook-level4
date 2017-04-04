package seedu.todolist.model;

import static seedu.todolist.commons.core.GlobalConstants.DATE_FORMAT;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.transformation.FilteredList;
import seedu.todolist.commons.core.ComponentManager;
import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.commons.events.model.TodoListChangedEvent;
import seedu.todolist.commons.events.storage.SaveFilePathChangedEvent;
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
     * Holds the previous states of the todo list
     */
    private final Stack<ReadOnlyTodoList> previousStates = new Stack<ReadOnlyTodoList>();
    private final Stack<ReadOnlyTodoList> nextStates = new Stack<ReadOnlyTodoList>();
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
    public ReadOnlyTodoList getTodoList() {
        return todoList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTodoListChanged() {
        raise(new TodoListChangedEvent(todoList));
    }

    //@@author A0163786N
    /** Helper function to handle storing previous state */
    private void handleStateChange(ReadOnlyTodoList todoList) {
        previousStates.push(new TodoList(todoList));
        if (!nextStates.empty()) {
            nextStates.clear();
        }
    }
    //@@author
    @Override
    public void resetData(ReadOnlyTodoList newData) {
        todoList.resetData(newData);
        indicateTodoListChanged();
    }
    //@@author A0163786N
    @Override
    public void clearData() {
        handleStateChange(todoList);
        resetData(new TodoList());
    }
    //@@author
    @Override
    public synchronized void deleteTodo(ReadOnlyTodo target) throws TodoNotFoundException {
        TodoList tempTodoList = new TodoList(todoList);
        todoList.removeTodo(target);
        handleStateChange(tempTodoList);
        indicateTodoListChanged();
    }

    @Override
    public synchronized void addTodo(Todo todo) throws UniqueTodoList.DuplicateTodoException {
        TodoList tempTodoList = new TodoList(todoList);
        todoList.addTodo(todo);
        handleStateChange(tempTodoList);
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
        handleStateChange(tempTodoList);
        indicateTodoListChanged();
    }
    //@@author A0163786N
    @Override
    public void completeTodo(int filteredTodoListIndex, Date completeTime) {
        handleStateChange(new TodoList(todoList));
        int todoListIndex = filteredTodos.getSourceIndex(filteredTodoListIndex);
        todoList.completeTodo(todoListIndex, completeTime);
        indicateTodoListChanged();
    }
    //@@author
    //@@author A0163786N
    @Override
    public void uncompleteTodo(int filteredTodoListIndex) {
        handleStateChange(new TodoList(todoList));
        int todoListIndex = filteredTodos.getSourceIndex(filteredTodoListIndex);
        todoList.uncompleteTodo(todoListIndex);
        indicateTodoListChanged();
    }
    //@@author
    //@@author A0163786N
    @Override
    public void loadPreviousState() throws NoPreviousStateException {
        if (previousStates.empty()) {
            throw new NoPreviousStateException();
        }
        nextStates.push(new TodoList(todoList));
        resetData(previousStates.pop());
    }
    //@@author
    //@@author A0163786N
    @Override
    public void loadNextState() throws NoNextStateException {
        if (nextStates.empty()) {
            throw new NoNextStateException();
        }
        previousStates.push(new TodoList(todoList));
        resetData(nextStates.pop());
    }
    //@@author

    @Subscribe
    public void handleSaveFileChangedEvent(SaveFilePathChangedEvent evt) {
        this.todoList.resetData(evt.data);
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
    //@@author A0163786N
    @Override
    public void updateFilteredTodoList(Set<String> keywords, Date startTime,
        Date endTime, Object completeTime, String todoType, UniqueTagList tags) {
        updateFilteredTodoList(new PredicateExpression(
                new NameQualifier(keywords, startTime, endTime, completeTime, todoType, tags)));
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
        private String todoType;

        NameQualifier(Set<String> nameKeyWords, Date startTime, Date endTime,
                Object completeTime, String todoType, UniqueTagList tags) {
            this.nameKeyWords = nameKeyWords;
            this.startTime = startTime;
            this.endTime = endTime;
            this.completeTime = completeTime;
            this.todoType = todoType;
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
                    && checkCompleteTime(todo) && checkTodoType(todo) && checkTags(todo);
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

        //@@author A0163720M
        /**
         * Checks todo type and returns true if todo should be shown in filtered list
         */
        private boolean checkTodoType(ReadOnlyTodo todo) {
            if (todoType != null) {
                Date startTime = todo.getStartTime();
                Date endTime = todo.getEndTime();

                switch (todoType) {
                    case "floating":
                        return startTime == null && endTime == null;
                    case "deadline":
                        return startTime == null && endTime != null;
                    case "event":
                        return startTime != null && endTime != null;
                    default:
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
