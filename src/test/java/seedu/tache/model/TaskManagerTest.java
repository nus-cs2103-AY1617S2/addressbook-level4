package seedu.tache.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
import seedu.tache.model.tag.Tag;
import seedu.tache.model.task.DetailedTask;
import seedu.tache.model.task.ReadOnlyDetailedTask;
import seedu.tache.model.task.ReadOnlyTask;
import seedu.tache.model.task.Task;
import seedu.tache.testutil.TypicalTestDetailedTasks;
import seedu.tache.testutil.TypicalTestTasks;

public class TaskManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TaskManager taskManager = new TaskManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskManager.getTaskList());
        assertEquals(Collections.emptyList(), taskManager.getDetailedTaskList());
        assertEquals(Collections.emptyList(), taskManager.getTagList());
    }

    @Test
    public void resetDataNullThrowsAssertionError() {
        thrown.expect(AssertionError.class);
        taskManager.resetData(null);
        fail();
    }

    @Test
    public void resetDataWithValidReadOnlyTaskManagerReplacesData() {
        TaskManager newData = new TypicalTestTasks().getTypicalTaskManager();
        taskManager.resetData(newData);
        assertEquals(newData, taskManager);
    }

    @Test
    public void resetDataWithDuplicateTasksThrowsAssertionError() {
        TypicalTestTasks td = new TypicalTestTasks();
        TypicalTestDetailedTasks ttdt = new TypicalTestDetailedTasks();
        // Repeat td.eggsAndBread twice
        List<Task> newTasks = Arrays.asList(new Task(td.eggsAndBread), new Task(td.eggsAndBread));
        List<DetailedTask> newDetailedTasks = Arrays.asList(new DetailedTask(ttdt.taskA), new DetailedTask(ttdt.taskA));
        List<Tag> newTags = td.eggsAndBread.getTags().asObservableList();
        TaskManagerStub newData = new TaskManagerStub(newTasks, newDetailedTasks, newTags);

        thrown.expect(AssertionError.class);
        taskManager.resetData(newData);
        fail();
    }

    @Test
    public void resetDataWithDuplicateTagsThrowsAssertionError() {
        TaskManager typicalTaskManager = new TypicalTestTasks().getTypicalTaskManager();
        List<ReadOnlyTask> newTasks = typicalTaskManager.getTaskList();
        List<ReadOnlyDetailedTask> newDetailedTasks = typicalTaskManager.getDetailedTaskList();
        List<Tag> newTags = new ArrayList<>(typicalTaskManager.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        TaskManagerStub newData = new TaskManagerStub(newTasks, newDetailedTasks, newTags);

        thrown.expect(AssertionError.class);
        taskManager.resetData(newData);
        fail();
    }

    /**
     * A stub ReadOnlyTaskManager whose tasks and tags lists can violate interface constraints.
     */
    private static class TaskManagerStub implements ReadOnlyTaskManager {
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<ReadOnlyDetailedTask> detailedTasks = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        TaskManagerStub(Collection<? extends ReadOnlyTask> tasks,
                        Collection<? extends ReadOnlyDetailedTask> detailedTasks, Collection<? extends Tag> tags) {
            this.tasks.setAll(tasks);
            this.detailedTasks.setAll(detailedTasks);
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

        @Override
        public ObservableList<ReadOnlyDetailedTask> getDetailedTaskList() {
            return detailedTasks;
        }
    }

}
