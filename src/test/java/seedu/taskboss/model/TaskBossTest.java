package seedu.taskboss.model;

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
import seedu.taskboss.model.category.Tag;
import seedu.taskboss.model.task.ReadOnlyTask;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.testutil.TypicalTestTasks;

public class TaskBossTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TaskBoss taskBoss = new TaskBoss();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskBoss.getTaskList());
        assertEquals(Collections.emptyList(), taskBoss.getTagList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        taskBoss.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyTaskBoss_replacesData() {
        TaskBoss newData = new TypicalTestTasks().getTypicalTaskBoss();
        taskBoss.resetData(newData);
        assertEquals(newData, taskBoss);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() {
        TypicalTestTasks td = new TypicalTestTasks();
        // Repeat td.alice twice
        List<Task> newTasks = Arrays.asList(new Task(td.alice), new Task(td.alice));
        List<Tag> newTags = td.alice.getTags().asObservableList();
        TaskBossStub newData = new TaskBossStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        taskBoss.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        TaskBoss typicalTaskBoss = new TypicalTestTasks().getTypicalTaskBoss();
        List<ReadOnlyTask> newTasks = typicalTaskBoss.getTaskList();
        List<Tag> newTags = new ArrayList<>(typicalTaskBoss.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        TaskBossStub newData = new TaskBossStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        taskBoss.resetData(newData);
    }

    /**
     * A stub ReadOnlyTaskBoss whose tasks and tags lists can violate interface constraints.
     */
    private static class TaskBossStub implements ReadOnlyTaskBoss {
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        TaskBossStub(Collection<? extends ReadOnlyTask> tasks, Collection<? extends Tag> tags) {
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
