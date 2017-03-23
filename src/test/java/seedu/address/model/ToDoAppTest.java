package seedu.address.model;

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
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.Task;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TypicalTestTasks;

public class ToDoAppTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ToDoApp toDoApp = new ToDoApp();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), toDoApp.getTaskList());
        assertEquals(Collections.emptyList(), toDoApp.getTagList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        toDoApp.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyToDoApp_replacesData() {
        ToDoApp newData = new TypicalTestTasks().getTypicalToDoApp();
        toDoApp.resetData(newData);
        assertEquals(newData, toDoApp);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() {
        TypicalTestTasks td = new TypicalTestTasks();
        // Repeat td.alice twice
        List<Task> newTasks = Arrays.asList(new Task(td.alice), new Task(td.alice));
        List<Tag> newTags = td.alice.getTags().asObservableList();
        ToDoAppStub newData = new ToDoAppStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        toDoApp.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        ToDoApp typicalToDoApp = new TypicalTestTasks().getTypicalToDoApp();
        List<ReadOnlyTask> newTasks = typicalToDoApp.getTaskList();
        List<Tag> newTags = new ArrayList<>(typicalToDoApp.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        ToDoAppStub newData = new ToDoAppStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        toDoApp.resetData(newData);
    }

    /**
     * A stub ReadOnlyToDoApp whose tasks and tags lists can violate interface constraints.
     */
    private static class ToDoAppStub implements ReadOnlyToDoApp {
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        ToDoAppStub(Collection<? extends ReadOnlyTask> tasks, Collection<? extends Tag> tags) {
            this.tasks.setAll(tasks);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<ReadOnlyTask> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
