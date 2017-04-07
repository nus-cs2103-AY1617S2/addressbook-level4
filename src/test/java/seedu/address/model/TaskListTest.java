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
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.testutil.TypicalTestTasks;

public class TaskListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TaskList taskList = new TaskList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskList.getTaskList());
        assertEquals(Collections.emptyList(), taskList.getTagList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        taskList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyTaskList_replacesData() {
        TaskList newData = new TypicalTestTasks().getTypicalTaskList();
        taskList.resetData(newData);
        assertEquals(newData, taskList);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() {
        TypicalTestTasks td = new TypicalTestTasks();
        // Repeat td.amuseFriend twice
        List<Task> newTasks;
        try {
            newTasks = Arrays.asList(new Task(td.amuseFriend), new Task(td.amuseFriend));
        } catch (IllegalValueException e) {
            throw new AssertionError("A TestTask should have been built correctly, otherwise copying will not fail.");
        }
        List<Tag> newTags = td.amuseFriend.getTags().asObservableList();
        TaskListStub newData = new TaskListStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        taskList.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        TaskList typicalTaskList = new TypicalTestTasks().getTypicalTaskList();
        List<ReadOnlyTask> newTasks = typicalTaskList.getTaskList();
        List<Tag> newTags = new ArrayList<>(typicalTaskList.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        TaskListStub newData = new TaskListStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        taskList.resetData(newData);
    }

    /**
     * A stub ReadOnlyTaskList whose tasks and tags lists can violate interface constraints.
     */
    private static class TaskListStub implements ReadOnlyTaskList {
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        TaskListStub(Collection<? extends ReadOnlyTask> tasks, Collection<? extends Tag> tags) {
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
