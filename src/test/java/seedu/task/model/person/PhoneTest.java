package seedu.task.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.model.task.StartTime;

public class PhoneTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
//        assertFalse(StartTime.isValidTime("")); // empty string
//        assertFalse(StartTime.isValidTime(" ")); // spaces only
        assertFalse(StartTime.isValidTime("phone")); // non-numeric
        assertFalse(StartTime.isValidTime("9011p041")); // alphabets within digits
        assertFalse(StartTime.isValidTime("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(StartTime.isValidTime("931211 1534"));
//        assertTrue(StartTime.isValidTime("4")); // short phone numbers
//        assertTrue(StartTime.isValidTime("124293842033123")); // long phone numbers
    }
}
