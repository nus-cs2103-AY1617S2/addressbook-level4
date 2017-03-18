package seedu.bulletjournal.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DeadlineTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(DueDate.isValidPhone("")); // empty string
        assertFalse(DueDate.isValidPhone(" ")); // spaces only
        assertFalse(DueDate.isValidPhone("phone")); // non-numeric
        assertFalse(DueDate.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(DueDate.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(DueDate.isValidPhone("93121534"));
        assertTrue(DueDate.isValidPhone("4")); // short phone numbers
        assertTrue(DueDate.isValidPhone("124293842033123")); // long phone numbers
    }
}
