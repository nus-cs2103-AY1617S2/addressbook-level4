package seedu.taskboss.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author A0144904H
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
        assertTrue(PriorityLevel.isValidPriorityLevel("Yes"));
        assertTrue(PriorityLevel.isValidPriorityLevel("No"));
    }
}
