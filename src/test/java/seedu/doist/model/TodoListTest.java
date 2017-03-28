package seedu.doist.model;

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
import seedu.doist.model.tag.Tag;
import seedu.doist.model.task.ReadOnlyTask;
import seedu.doist.model.task.Task;
import seedu.doist.testutil.TypicalTestTasks;

public class TodoListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TodoList todoList = new TodoList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), todoList.getTaskList());
        assertEquals(Collections.emptyList(), todoList.getTagList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        todoList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        TodoList newData = new TypicalTestTasks().getTypicalTodoList();
        todoList.resetData(newData);
        assertEquals(newData, todoList);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        TypicalTestTasks td = new TypicalTestTasks();
        // Repeat td.alice twice
        List<Task> newPersons = Arrays.asList(new Task(td.laundry), new Task(td.laundry));
        List<Tag> newTags = td.laundry.getTags().asObservableList();
        TodoListStub newData = new TodoListStub(newPersons, newTags);

        thrown.expect(AssertionError.class);
        todoList.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        TodoList typicalAddressBook = new TypicalTestTasks().getTypicalTodoList();
        List<ReadOnlyTask> newPersons = typicalAddressBook.getTaskList();
        List<Tag> newTags = new ArrayList<>(typicalAddressBook.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        TodoListStub newData = new TodoListStub(newPersons, newTags);

        thrown.expect(AssertionError.class);
        todoList.resetData(newData);
    }

    /**
     * A stub ReadOnlyTodoList whose persons and tags lists can violate interface constraints.
     */
    private static class TodoListStub implements ReadOnlyTodoList {
        private final ObservableList<ReadOnlyTask> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        TodoListStub(Collection<? extends ReadOnlyTask> persons, Collection<? extends Tag> tags) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<ReadOnlyTask> getTaskList() {
            return persons;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
