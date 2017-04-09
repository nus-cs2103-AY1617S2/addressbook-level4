package seedu.task.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.task.testutil.TestDataHelper;

//@@author A0140063X
public class ModelManagerTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private Model model;
    private TestDataHelper helper;

    @Before
    public void setUp() {
        model = new ModelManager();
        helper = new TestDataHelper();
    }

    @Test
    public void addMultipleTasks_validTaskList_success() throws Exception {
        // setup expectations
        List<Task> generatedTasks = helper.generateTaskList(5);
        ArrayList<Task> taskList = new ArrayList<>();
        TaskManager expectedTM = new TaskManager();
        for (Task task : generatedTasks) {
            expectedTM.addTaskToFront(task);
            taskList.add(0, task);
        }

        model.addMultipleTasks(taskList);

        assertTrue(model.getTaskManager().equals(expectedTM));
    }

    @Test
    public void addMultipleTasks_duplicateTask_notAdded() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        List<Task> generatedTasks = helper.generateTaskList(5);
        ArrayList<Task> taskList = new ArrayList<>();
        TaskManager expectedTM = new TaskManager();
        for (Task task : generatedTasks) {
            expectedTM.addTaskToFront(task);
            taskList.add(0, task);
        }

        // duplicate a task
        taskList.add(0, generatedTasks.get(0));

        model.addMultipleTasks(taskList);

        assertTrue(model.getTaskManager().equals(expectedTM));
    }

    @Test
    public void setTaskEventId_validInput_sucess() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task testTask = helper.allocate();
        model.addTask(testTask);

        Task expectedTask = testTask;
        String validEventId = "sampleventid123";
        expectedTask.setEventId(validEventId);

        model.setTaskEventId(testTask, validEventId);

        assertTrue(expectedTask.equals(testTask));
    }

    @Test
    public void setTaskEventId_invalidEventId_exceptionThrown() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task testTask = helper.allocate();
        model.addTask(testTask);

        exception.expect(IllegalValueException.class);

        String invalidEventId = "UPPERCASE";
        model.setTaskEventId(testTask, invalidEventId);

        invalidEventId = "space in between";
        model.setTaskEventId(testTask, invalidEventId);

        invalidEventId = "four";
        model.setTaskEventId(testTask, invalidEventId);

        invalidEventId = "$ym6015";
        model.setTaskEventId(testTask, invalidEventId);
    }

    @Test
    public void setTaskEventId_taskNotInModel_exceptionThrown() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task testTask = helper.allocate();

        String validEventId = "sampleventid123";

        // testTask was not added to model
        exception.expect(TaskNotFoundException.class);
        model.setTaskEventId(testTask, validEventId);

    }
    // @@author

    @Test
    public void getUserPrefs_success() {
        ModelManager model = new ModelManager();
        assertFalse(model.getUserPrefs().equals(null));
    }

}
