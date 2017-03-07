package seedu.taskmanager.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskmanager.model.task.Time;

public class PhoneTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only
        assertFalse(Time.isValidTime("phone")); // non-numeric
        assertFalse(Time.isValidTime("9011p041")); // alphabets within digits
        assertFalse(Time.isValidTime("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Time.isValidTime("93121534"));
        assertTrue(Time.isValidTime("4")); // short phone numbers
        assertTrue(Time.isValidTime("124293842033123")); // long phone numbers
    }
}
