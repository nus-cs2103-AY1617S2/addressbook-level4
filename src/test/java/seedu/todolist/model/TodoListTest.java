package seedu.todolist.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.todo.ReadOnlyTodo;
import seedu.todolist.model.todo.Todo;
import seedu.todolist.testutil.TypicalTestTodos;

public class TodoListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TodoList addressBook = new TodoList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getTodoList());
        assertEquals(Collections.emptyList(), addressBook.getTagList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyTodoList_replacesData() {
        TodoList newData = new TypicalTestTodos().getTypicalTodoList();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateTodos_throwsAssertionError() {
        TypicalTestTodos td = new TypicalTestTodos();
        // Repeat td.alice twice
        List<Todo> newTodos = Arrays.asList(new Todo(td.dog), new Todo(td.dog));
        List<Tag> newTags = td.dog.getTags().asObservableList();
        TodoListStub newData = new TodoListStub(newTodos, newTags);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        TodoList typicalTodoList = new TypicalTestTodos().getTypicalTodoList();
        List<ReadOnlyTodo> newTodos = typicalTodoList.getTodoList();
        List<Tag> newTags = new ArrayList<>(typicalTodoList.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        TodoListStub newData = new TodoListStub(newTodos, newTags);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    /**
     * A stub ReadOnlyTodoList whose todos and tags lists can violate interface constraints.
     */
    private static class TodoListStub implements ReadOnlyTodoList {
        private final ObservableList<ReadOnlyTodo> todos = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        TodoListStub(Collection<? extends ReadOnlyTodo> todos, Collection<? extends Tag> tags) {
            this.todos.setAll(todos);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<ReadOnlyTodo> getTodoList() {
            return todos;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
