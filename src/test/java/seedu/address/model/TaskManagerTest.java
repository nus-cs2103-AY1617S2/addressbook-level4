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
import seedu.address.testutil.TypicalTestTasks;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.TaskManager;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;

public class TaskManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TaskManager taskManager = new TaskManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskManager.getTaskList());
        assertEquals(Collections.emptyList(), taskManager.getTagList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        taskManager.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyTaskManager_replacesData() {
        TaskManager newData = new TypicalTestTasks().getTypicalTaskManger();
        taskManager.resetData(newData);
        assertEquals(newData, taskManager);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        TypicalTestTasks td = new TypicalTestTasks();
        // Repeat td.alice twice
        List<Task> newTasks = Arrays.asList(new Task(td.task11), new Task(td.task11));
        List<Task> completedTasks = Arrays.asList(new Task(td.task33));
        List<Tag> newTags = td.task11.getTags().asObservableList();
        TaskManagerStub newData = new TaskManagerStub(newTasks, completedTasks, newTags);

        thrown.expect(AssertionError.class);
        taskManager.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        TaskManager typicalAddressBook = new TypicalTestTasks().getTypicalTaskManger();
        List<ReadOnlyTask> newTasks = typicalAddressBook.getTaskList();
        List<ReadOnlyTask> completedTasks = typicalAddressBook.getCompletedTaskList();
        List<Tag> newTags = new ArrayList<>(typicalAddressBook.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        TaskManagerStub newData = new TaskManagerStub(newTasks, completedTasks, newTags);

        thrown.expect(AssertionError.class);
        taskManager.resetData(newData);
    }

    /**
     * A stub ReadOnlyTaskManager whose tasks and tags lists can violate interface constraints.
     */
    private static class TaskManagerStub implements ReadOnlyTaskManager {
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<ReadOnlyTask> completedTasks = FXCollections.observableArrayList();
        private final ObservableList<ReadOnlyTask> overdueTasks = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        TaskManagerStub(Collection<? extends ReadOnlyTask> tasks,
                Collection<? extends ReadOnlyTask> completedTasks, Collection<? extends Tag> tags) {
            this.tasks.setAll(tasks);
            this.tags.setAll(tags);
            this.completedTasks.setAll(completedTasks);
        }

        @Override
        public ObservableList<ReadOnlyTask> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }

        @Override
        public ObservableList<ReadOnlyTask> getCompletedTaskList() {
            return completedTasks;
        }

        @Override
        public ObservableList<ReadOnlyTask> getOverdueTaskList() {
            return overdueTasks;
        }
    }

}
