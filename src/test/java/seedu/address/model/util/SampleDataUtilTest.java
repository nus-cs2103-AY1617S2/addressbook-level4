//@@author A0139539R
package seedu.address.model.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.task.Task;

public class SampleDataUtilTest {

    @Test
    public void getSampleTasksTest() {
        Task[] sampleTasks = SampleDataUtil.getSampleTasks();
        assertNotNull(sampleTasks);
        assertTrue(sampleTasks.length == SampleDataUtil.SAMPLE_TASKS_LENGTH);
    }

    @Test
    public void getSampleAddressBookTest() {
        ReadOnlyAddressBook sampleAB = SampleDataUtil.getSampleAddressBook();
        assertNotNull(sampleAB);
        assertTrue(sampleAB.getTaskList().size() == SampleDataUtil.SAMPLE_TASKS_LENGTH);
    }

}
