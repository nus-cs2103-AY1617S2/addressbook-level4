package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import seedu.address.model.label.Label;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.testutil.TypicalTestTasks;

public class TaskManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TaskManager taskManager = new TaskManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskManager.getTaskList());
        assertEquals(Collections.emptyList(), taskManager.getLabelList());
    }

    @Test
    public void resetData_null_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        taskManager.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        TaskManager newData = new TypicalTestTasks().getTypicalTaskManager();
        taskManager.resetData(newData);
        assertEquals(newData, taskManager);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() {
        TypicalTestTasks td = new TypicalTestTasks();
        // Repeat td.alice twice
        List<Task> newTasks = Arrays.asList(new Task(td.task1), new Task(td.task1));
        List<Label> newLabels = td.task1.getLabels().asObservableList();
        TaskManagerStub newData = new TaskManagerStub(newTasks, newLabels);

        thrown.expect(AssertionError.class);
        taskManager.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateLabels_throwsAssertionError() {
        TaskManager typicalTaskManager = new TypicalTestTasks().getTypicalTaskManager();
        List<ReadOnlyTask> newTasks = typicalTaskManager.getTaskList();
        List<Label> newLabels = new ArrayList<>(typicalTaskManager.getLabelList());
        // Repeat the first label twice
        newLabels.add(newLabels.get(0));
        TaskManagerStub newData = new TaskManagerStub(newTasks, newLabels);

        thrown.expect(AssertionError.class);
        taskManager.resetData(newData);
    }

    @Test
    public void taskManager_TestHashCode() {
        TaskManager typicalTaskManager = new TypicalTestTasks().getTypicalTaskManager();
        assertTrue(typicalTaskManager.hashCode() == typicalTaskManager.hashCode());
    }

    @Test
    public void taskManager_TestEquals() throws TaskNotFoundException {
        TaskManager typicalTaskManager1 = new TypicalTestTasks().getTypicalTaskManager();
        TaskManager typicalTaskManager2 = new TypicalTestTasks().getTypicalTaskManager();

        //Test equal if contain the same stuff
        assertTrue(typicalTaskManager1.equals(typicalTaskManager1));
        assertTrue(typicalTaskManager1.equals(typicalTaskManager2));

        //Test different
        //remove first element in typicalTaskManager2
        typicalTaskManager2.removeTask(typicalTaskManager2.getTaskList().get(0));
        assertFalse(typicalTaskManager1.equals(typicalTaskManager2));

        //Test null
        assertFalse(typicalTaskManager1.equals(null));
    }

    /**
     * A stub ReadOnlyTaskManager whose tasks and labels lists can violate interface constraints.
     */
    private static class TaskManagerStub implements ReadOnlyTaskManager {
        private final ObservableList<ReadOnlyTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<Label> labels = FXCollections.observableArrayList();

        TaskManagerStub(Collection<? extends ReadOnlyTask> tasks, Collection<? extends Label> labels) {
            this.tasks.setAll(tasks);
            this.labels.setAll(labels);
        }

        @Override
        public ObservableList<ReadOnlyTask> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Label> getLabelList() {
            return labels;
        }

        @Override
        public ObservableList<ReadOnlyTask> getImmutableTaskList() throws CloneNotSupportedException {
            return getTaskList();
        }

        @Override
        public ObservableList<Label> getImmutableLabelList() throws CloneNotSupportedException {
            return getLabelList();
        }
    }

}
