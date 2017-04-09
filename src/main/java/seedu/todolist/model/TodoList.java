package seedu.todolist.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.todo.ReadOnlyTodo;
import seedu.todolist.model.todo.Todo;
import seedu.todolist.model.todo.UniqueTodoList;
import seedu.todolist.model.todo.UniqueTodoList.DuplicateTodoException;
import seedu.todolist.model.todo.UniqueTodoList.TodoNotFoundException;

/**
 * Wraps all data at the todolist level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TodoList implements ReadOnlyTodoList {

    private final UniqueTodoList todos;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        todos = new UniqueTodoList();
        tags = new UniqueTagList();
    }

    public TodoList() {}

    /**
     * Creates an TodoList using the todos and Tags in the {@code toBeCopied}
     */
    public TodoList(ReadOnlyTodoList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public Todo getTodo(int index) throws TodoNotFoundException {
        return this.todos.getTodo(index);
    }

//// list overwrite operations

    public void settodos(List<? extends ReadOnlyTodo> todos)
            throws UniqueTodoList.DuplicateTodoException {
        this.todos.setTodos(todos);
    }

    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
        this.tags.setTags(tags);
    }

    public void resetData(ReadOnlyTodoList newData) {
        assert newData != null;
        try {
            settodos(newData.getTodoList());
        } catch (UniqueTodoList.DuplicateTodoException e) {
            assert false : "TodoLists should not have duplicate todos";
        }
        try {
            setTags(newData.getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            assert false : "TodoLists should not have duplicate tags";
        }
        syncMasterTagListWith(todos);
    }

//// todo-level operations

    /**
     * Adds a todo to the todo list.
     * Also checks the new todo's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the todo to point to those in {@link #tags}.
     *
     * @throws UniqueTodoList.DuplicateTodoException if an equivalent todo already exists.
     */
    public void addTodo(Todo p) throws UniqueTodoList.DuplicateTodoException {
        syncMasterTagListWith(p);
        todos.add(p);
    }
    //@@author A0165043M
    /**
     * Updates the todo in the list at position {@code index} with {@code editedReadOnlyTodo}.
     * {@code TodoList}'s tag list will be updated with the tags of {@code editedReadOnlyTodo}.
     * @see #syncMasterTagListWith(Todo)
     *
     * @throws DuplicateTodoException if updating the todo's details causes the todo to be equivalent to
     *      another existing todo in the list.
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTodo(int index, ReadOnlyTodo editedReadOnlyTodo)
            throws UniqueTodoList.DuplicateTodoException {
        assert editedReadOnlyTodo != null;
        Todo editedTodo = createEditedTodoWithTime(editedReadOnlyTodo, todos.asObservableList().get(index));
        syncMasterTagListWith(editedTodo);
        // Todo: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any todo
        // in the todo list.
        todos.updateTodo(index, editedTodo);
    }
    //@@author
    //@@author A0163786N
    /**
     * Completes the todo in the list at position {@code index} with {@code completeTime}.
     */
    public void completeTodo(int index, Date completeTime) {
        todos.completeTodo(index, completeTime);
    }
    //@@author
    //@@author A0163786N
    /**
     * Uncompletes the todo in the list at position {@code index}.
     */
    public void uncompleteTodo(int index) {
        todos.uncompleteTodo(index);
    }
    //@@author
    //@@author A0165043M
    /**
     * return a Todo with proper time.
     * if the original todo already has time and the edited todo
     * doesn't include time, then edited todo won't replace new todo new
     * empty time.
     * @param editedReadOnlyTodo
     * @param originalTodo
     * @return Todo
     */
    private Todo createEditedTodoWithTime(ReadOnlyTodo editedReadOnlyTodo, Todo originalTodo) {
        Todo editedTodo = null;
        if (editedReadOnlyTodo.getStartTime() != null && editedReadOnlyTodo.getEndTime() != null) {
            editedTodo = new Todo(editedReadOnlyTodo.getName(), editedReadOnlyTodo.getStartTime(),
                     editedReadOnlyTodo.getEndTime(), editedReadOnlyTodo.getTags());
        } else if (editedReadOnlyTodo.getStartTime() == null && editedReadOnlyTodo.getEndTime() != null) {
            editedTodo = new Todo(editedReadOnlyTodo.getName(), editedReadOnlyTodo.getEndTime(),
                    editedReadOnlyTodo.getTags());
        } else if (originalTodo.getStartTime() != null && originalTodo.getEndTime() != null) {
            editedTodo = new Todo(editedReadOnlyTodo.getName(), originalTodo.getStartTime(),
                    originalTodo.getEndTime(), editedReadOnlyTodo.getTags());
        } else if (originalTodo.getStartTime() == null && originalTodo.getEndTime() != null) {
            editedTodo = new Todo(editedReadOnlyTodo.getName(), originalTodo.getEndTime(),
                    editedReadOnlyTodo.getTags());
        } else {
            editedTodo = new Todo(editedReadOnlyTodo);
        }
        return editedTodo;
    }
    //@@author
    /**
     * Ensures that every tag in this todo:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Todo todo) {
        final UniqueTagList todoTags = todo.getTags();
        tags.mergeFrom(todoTags);

        // Create map with values = tag object references in the master list
        // used for checking todo tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of todo tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        todoTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));

        todo.setTags(new UniqueTagList(correctTagReferences));
    }

    /**
     * Ensures that every tag in these todos:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     *  @see #syncMasterTagListWith(Todo)
     */
    private void syncMasterTagListWith(UniqueTodoList todos) {
        todos.forEach(this::syncMasterTagListWith);
    }

    public boolean removeTodo(ReadOnlyTodo key) throws UniqueTodoList.TodoNotFoundException {
        if (todos.remove(key)) {
            return true;
        } else {
            throw new UniqueTodoList.TodoNotFoundException();
        }
    }

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return todos.asObservableList().size() + " todos, " + tags.asObservableList().size() +  " tags";
        // Todo: refine later
    }

    @Override
    public ObservableList<ReadOnlyTodo> getTodoList() {
        return new UnmodifiableObservableList<>(todos.asObservableList());
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return new UnmodifiableObservableList<>(tags.asObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodoList // instanceof handles nulls
                && this.todos.equals(((TodoList) other).todos)
                && this.tags.equalsOrderInsensitive(((TodoList) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(todos, tags);
    }
}
