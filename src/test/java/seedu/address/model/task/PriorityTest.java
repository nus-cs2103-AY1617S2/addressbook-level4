package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PriorityTest {

    @Test
    public void isValidPriority() {
        // blank priorities
        assertFalse(Priority.isValidPriority(" ")); // spaces only

        // invalid priorities
        assertFalse(Priority.isValidPriority("^^"));
        assertFalse(Priority.isValidPriority("@##@#"));
        assertFalse(Priority.isValidPriority("{{{{{{{{"));

        // valid priorities
        assertTrue(Priority.isValidPriority("1"));
        assertTrue(Priority.isValidPriority("2"));
        assertTrue(Priority.isValidPriority("3"));
        assertTrue(Priority.isValidPriority("4"));
        assertTrue(Priority.isValidPriority("5"));
        assertTrue(Priority.isValidPriority("-1"));
        assertTrue(Priority.isValidPriority("-2"));
        assertTrue(Priority.isValidPriority("-3"));
    }
}
