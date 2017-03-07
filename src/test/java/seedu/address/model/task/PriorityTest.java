package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PriorityTest {

    @Test
    public void isValidPriority() {
        // invalid phone numbers
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only

        // valid phone numbers
        assertTrue(Priority.isValidPriority("93121534"));
        assertTrue(Priority.isValidPriority("4")); // short phone numbers
        assertTrue(Priority.isValidPriority("124293842033123")); // long phone numbers
    }
}
