package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.model.task.PriorityLevel;

public class PriorityLevelTest {

    @Test
    public void isValidPriority() {
        // blank priority
        assertFalse(PriorityLevel.isValidPriorityLevel("")); // empty string
        assertFalse(PriorityLevel.isValidPriorityLevel(" ")); // spaces only

        // invalid priority inputs
        assertFalse(PriorityLevel.isValidPriorityLevel("-5"));
        assertFalse(PriorityLevel.isValidPriorityLevel("5"));
        assertFalse(PriorityLevel.isValidPriorityLevel("LOW"));
        assertFalse(PriorityLevel.isValidPriorityLevel("HIGH"));

        // valid priority inputs
        assertTrue(PriorityLevel.isValidPriorityLevel("1"));
        assertTrue(PriorityLevel.isValidPriorityLevel("2"));
        assertTrue(PriorityLevel.isValidPriorityLevel("3"));
        assertTrue(PriorityLevel.isValidPriorityLevel("4"));
    }
}
