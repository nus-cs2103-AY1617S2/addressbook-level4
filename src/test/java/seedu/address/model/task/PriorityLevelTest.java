package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PriorityLevelTest {

    @Test
    public void isValidEmail() {
        // blank priority
        assertFalse(PriorityLevel.isValidPriorityLevel("")); // empty string
        assertFalse(PriorityLevel.isValidPriorityLevel(" ")); // spaces only

        // invalid priority digits
        assertFalse(PriorityLevel.isValidPriorityLevel("-12"));
        assertFalse(PriorityLevel.isValidPriorityLevel("asd"));
        assertFalse(PriorityLevel.isValidPriorityLevel("5"));

        // valid priority digits
        assertTrue(PriorityLevel.isValidPriorityLevel("1"));
        assertTrue(PriorityLevel.isValidPriorityLevel("2"));
        assertTrue(PriorityLevel.isValidPriorityLevel("3"));
        assertTrue(PriorityLevel.isValidPriorityLevel("4"));
    }
}
