package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.model.task.Description;

public class DescriptionTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Description.isValidPhone("")); // empty string
        assertFalse(Description.isValidPhone(" ")); // spaces only
        assertFalse(Description.isValidPhone("phone")); // non-numeric
        assertFalse(Description.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Description.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Description.isValidPhone("93121534"));
        assertTrue(Description.isValidPhone("4")); // short phone numbers
        assertTrue(Description.isValidPhone("124293842033123")); // long phone numbers
    }
}
