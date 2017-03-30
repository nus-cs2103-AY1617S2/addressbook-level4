package seedu.jobs.model;

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
import seedu.jobs.model.tag.Tag;
import seedu.jobs.model.task.ReadOnlyTask;
import seedu.jobs.model.task.Task;
import seedu.jobs.testutil.TypicalTestTasks;

public class TaskBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TaskBook taskBook = new TaskBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskBook.getTaskList());
        assertEquals(Collections.emptyList(), taskBook.getTagList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        taskBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        TaskBook newData = new TypicalTestTasks().getTypicalTaskBook();
        taskBook.resetData(newData);
        assertEquals(newData, taskBook);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() {
        TypicalTestTasks td = new TypicalTestTasks();
        // Repeat td.alice twice
        List<Task> newTasks = Arrays.asList(new Task(td.CS3101), new Task(td.CS3101));
        List<Tag> newTags = td.CS3101.getTags().asObservableList();
        TaskBookStub newData = new TaskBookStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        taskBook.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        TaskBook typicalTaskBook = new TypicalTestTasks().getTypicalTaskBook();
        ObservableList<ReadOnlyTask> newTask = typicalTaskBook.getTaskList();
        List<Tag> newTags = new ArrayList<>(typicalTaskBook.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        TaskBookStub newData = new TaskBookStub(newTask, newTags);

        thrown.expect(AssertionError.class);
        taskBook.resetData(newData);
    }

    /**
     * A stub ReadOnlyTaskBook whose persons and tags lists can violate interface constraints.
     */
    private static class TaskBookStub implements ReadOnlyTaskBook {
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        TaskBookStub(Collection<? extends ReadOnlyTask> tasks, Collection<? extends Tag> tags) {
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
