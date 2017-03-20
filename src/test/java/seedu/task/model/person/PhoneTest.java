package seedu.task.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.model.task.Priority;

public class PhoneTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("phone")); // non-numeric
        assertFalse(Priority.isValidPriority("9011p041")); // alphabets within digits
        assertFalse(Priority.isValidPriority("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Priority.isValidPriority("93121534"));
        assertTrue(Priority.isValidPriority("4")); // short phone numbers
        assertTrue(Priority.isValidPriority("124293842033123")); // long phone numbers
    }
}
