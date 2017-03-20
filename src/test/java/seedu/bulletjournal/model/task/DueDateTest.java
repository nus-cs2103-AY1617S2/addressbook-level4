package seedu.bulletjournal.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DueDateTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(DueDate.isValidDueDate("")); // empty string
        assertFalse(DueDate.isValidDueDate(" ")); // spaces only
        assertFalse(DueDate.isValidDueDate("phone")); // non-numeric
        assertFalse(DueDate.isValidDueDate("9011p041")); // alphabets within digits
        assertFalse(DueDate.isValidDueDate("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(DueDate.isValidDueDate("93121534"));
        assertTrue(DueDate.isValidDueDate("4")); // short phone numbers
        assertTrue(DueDate.isValidDueDate("124293842033123")); // long phone numbers
    }
}
