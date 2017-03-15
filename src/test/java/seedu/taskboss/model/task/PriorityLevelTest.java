package seedu.taskboss.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PriorityLevelTest {

    @Test
    public void isValidPriorityLevel() {
        // invalid priority level
        assertFalse(PriorityLevel.isValidPriorityLevel(" ")); // spaces only
        assertFalse(PriorityLevel.isValidPriorityLevel("priorityLevel")); // non-numeric
        assertFalse(PriorityLevel.isValidPriorityLevel("9")); // invalid range
        assertFalse(PriorityLevel.isValidPriorityLevel("0")); // invalid range

        // valid priority level
        assertTrue(PriorityLevel.isValidPriorityLevel(""));
        assertTrue(PriorityLevel.isValidPriorityLevel("1"));
        assertTrue(PriorityLevel.isValidPriorityLevel("2"));
        assertTrue(PriorityLevel.isValidPriorityLevel("3"));
    }
}
