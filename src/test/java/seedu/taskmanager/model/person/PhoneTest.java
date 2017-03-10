package seedu.taskmanager.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskmanager.model.task.StartDate;

public class PhoneTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(StartDate.isValidStartDate("")); // empty string
        assertFalse(StartDate.isValidStartDate(" ")); // spaces only
        assertFalse(StartDate.isValidStartDate("phone")); // non-numeric
        assertFalse(StartDate.isValidStartDate("9011p041")); // alphabets within digits
        assertFalse(StartDate.isValidStartDate("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(StartDate.isValidStartDate("93121534"));
        assertTrue(StartDate.isValidStartDate("4")); // short phone numbers
        assertTrue(StartDate.isValidStartDate("124293842033123")); // long phone numbers
    }
}
