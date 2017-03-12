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
        assertTrue(Priority.isValidPriority("none")); // no priority
        assertTrue(Priority.isValidPriority("hi")); // high priority
        assertTrue(Priority.isValidPriority("mid")); // medium priority
        assertTrue(Priority.isValidPriority("low")); // low priority
    }
}
