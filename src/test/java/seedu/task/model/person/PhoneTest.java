package seedu.task.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.model.task.StartDate;

public class PhoneTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(StartDate.isValidDate("")); // empty string
        assertFalse(StartDate.isValidDate(" ")); // spaces only
        assertFalse(StartDate.isValidDate("phone")); // non-numeric
        assertFalse(StartDate.isValidDate("9011p041")); // alphabets within digits
        assertFalse(StartDate.isValidDate("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(StartDate.isValidDate("93121534"));
        assertTrue(StartDate.isValidDate("4")); // short phone numbers
        assertTrue(StartDate.isValidDate("124293842033123")); // long phone numbers
    }
}
