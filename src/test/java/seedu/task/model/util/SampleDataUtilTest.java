//@@author A0139539R
package seedu.task.model.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.task.Task;

public class SampleDataUtilTest {

    @Test
    public void getSampleTasksTest() {
        Task[] sampleTasks = SampleDataUtil.getSampleTasks();
        assertNotNull(sampleTasks);
        assertTrue(sampleTasks.length == SampleDataUtil.SAMPLE_TASKS_LENGTH);
    }

    @Test
    public void getSampleTaskManagerTest() {
        ReadOnlyTaskManager sampleAB = SampleDataUtil.getSampleTaskManager();
        assertNotNull(sampleAB);
        assertTrue(sampleAB.getTaskList().size() == SampleDataUtil.SAMPLE_TASKS_LENGTH);
    }

}
