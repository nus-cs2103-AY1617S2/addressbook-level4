package seedu.task.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Date;
import seedu.task.model.task.Location;
import seedu.task.model.task.Name;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Remark;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.task.testutil.TypicalTestTasks;

public class TaskManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TaskManager taskManager = new TaskManager();
    private final TaskManager expectedTaskManager = new TaskManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskManager.getTaskList());
        assertEquals(Collections.emptyList(), taskManager.getTagList());
    }

    @Test
    public void removeTask_null_throwsException() throws TaskNotFoundException, IllegalValueException {
        thrown.expect(UniqueTaskList.TaskNotFoundException.class);
        taskManager.removeTask(new Task(new Name("test"), new Date(), new Date(), new Remark(), new Location(),
                new UniqueTagList(), false, new String()));
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        taskManager.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyTaskManager_replacesData() {
        TaskManager newData = new TypicalTestTasks().getTypicalTaskManager();
        taskManager.resetData(newData);
        expectedTaskManager.resetData(newData);
        assertEquals(newData, taskManager);
    }

    @Test
    public void equal_symmetric() {
        assertTrue(taskManager.equals(expectedTaskManager));
        assertTrue(taskManager.hashCode() == expectedTaskManager.hashCode());
    }

    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() throws IllegalValueException {
        TypicalTestTasks td = new TypicalTestTasks();
        // Repeat td.alice twice
        List<Task> newTasks = Arrays.asList(new Task(td.apply), new Task(td.apply));
        List<Tag> newTags = td.apply.getTags().asObservableList();
        TaskManagerStub newData = new TaskManagerStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        taskManager.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        TaskManager typicalTaskManager = new TypicalTestTasks().getTypicalTaskManager();
        List<ReadOnlyTask> newTasks = typicalTaskManager.getTaskList();
        List<Tag> newTags = new ArrayList<>(typicalTaskManager.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        TaskManagerStub newData = new TaskManagerStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        taskManager.resetData(newData);
    }

    /**
     * A stub ReadOnlyTaskManager whose tasks and tags lists can violate interface constraints.
     */
    private static class TaskManagerStub implements ReadOnlyTaskManager {
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        TaskManagerStub(Collection<? extends ReadOnlyTask> tasks, Collection<? extends Tag> tags) {
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
