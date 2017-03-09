package seedu.doit.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.doit.model.task.Priority;

public class PriorityTest {

    @Test
    public void isValidPriority() {
        // invalid priority numbers
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("priority")); // non-numeric
        assertFalse(Priority.isValidPriority("9011p041")); // alphabets within digits
        assertFalse(Priority.isValidPriority("9312 1534")); // spaces within digits

        // valid priority numbers
        assertTrue(Priority.isValidPriority("93121534"));
        assertTrue(Priority.isValidPriority("4")); // short priority numbers
        assertTrue(Priority.isValidPriority("124293842033123")); // long priority numbers
    }
}
