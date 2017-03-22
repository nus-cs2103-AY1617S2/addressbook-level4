/* @@author A0119505J */
package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PriorityTest {

    @Test
    public void isPriorityLevel() {
        // invalid Priority
        assertFalse(Priority.isPriorityLevel("")); // empty string
        assertFalse(Priority.isPriorityLevel(" ")); // spaces only

        // valid Priority
        assertTrue(Priority.isPriorityLevel("high"));
        assertTrue(Priority.isPriorityLevel("low")); // one character
        assertTrue(Priority.isPriorityLevel("med")); // long address
    }
}
