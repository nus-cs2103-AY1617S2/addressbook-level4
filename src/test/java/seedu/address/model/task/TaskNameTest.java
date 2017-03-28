package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.model.task.TaskName;

public class TaskNameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(TaskName.isValidTaskName("")); // empty string
        assertFalse(TaskName.isValidTaskName(" ")); // spaces only
        assertFalse(TaskName.isValidTaskName("^")); // only non-alphanumeric characters
        assertFalse(TaskName.isValidTaskName("project*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(TaskName.isValidTaskName("project")); // alphabets only
        assertTrue(TaskName.isValidTaskName("2103")); // numbers only
        assertTrue(TaskName.isValidTaskName("cs2103t project")); // alphanumeric characters
        assertTrue(TaskName.isValidTaskName("Discussion of CS2103T Project with team members")); // long names
    }
}
