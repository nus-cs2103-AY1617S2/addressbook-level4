//@@author A0164212U
package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DescriptionTest {

    @Test
    public void isValidDescription() {
        // invalid name
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only
        // valid name
        assertTrue(Description.isValidDescription("finish homework")); // alphabets only
        assertTrue(Description.isValidDescription("this is a very long task that must be finished soon"));
        // long frequency
        assertTrue(Description.isValidDescription("Capitalize The Description")); // with capital letters
        assertTrue(Description.isValidDescription("2564")); // numbers only
        assertTrue(Description.isValidDescription("task1")); // contains numbers characters at end
        assertTrue(Description.isValidDescription("21task")); // contains numbers characters at beginning
        assertTrue(Description.isValidDescription("task@")); // contains non-alphanumeric characters at end
    }
}
