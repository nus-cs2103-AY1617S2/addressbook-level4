package seedu.whatsleft.model.activity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class PriorityTest {
    //@@author A0110491U
    @Test
    public void isValidPriority() {
        // invalid phone numbers
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("phone")); // alphabets not part of PriorityLevel enum
        assertFalse(Priority.isValidPriority("9011p041")); // alphabets within digits not part of PriorityLevel enum
        assertFalse(Priority.isValidPriority("9312 1534")); // spaces within digits not part of PriorityLevel enum

        // valid phone numbers
        assertTrue(Priority.isValidPriority("high"));
        assertTrue(Priority.isValidPriority("medium"));
        assertTrue(Priority.isValidPriority("low"));
    }
}
