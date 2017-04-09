package seedu.task.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;

public class TaskNameTest {

    @Test
    public void isValidName() {
        // invalid task name
        assertFalse(TaskName.isValidTaskName("")); // empty string
        assertFalse(TaskName.isValidTaskName(" ")); // spaces only
        assertFalse(TaskName.isValidTaskName("^")); // only non-alphanumeric
        // characters
        assertFalse(TaskName.isValidTaskName("Shopping*")); // contains
        // non-alphanumeric
        // characters

        // valid task name
        assertTrue(TaskName.isValidTaskName("Fix phone")); // alphabets only
        assertTrue(TaskName.isValidTaskName("Collect phone at 10am")); // alphanumeric
        // characters
        assertTrue(TaskName.isValidTaskName("Throw Phone")); // with capital
        // letters

        TaskName name1 = null;
        TaskName name2 = null;
        TaskName name3 = null;
        try {
            name1 = new TaskName("This is a name");
            name2 = new TaskName("This is a name");
            name3 = new TaskName("This name is longer");
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        assertTrue(name1.equals(name2));
        assertEquals(1, name3.compareTo(name1));

    }
}
