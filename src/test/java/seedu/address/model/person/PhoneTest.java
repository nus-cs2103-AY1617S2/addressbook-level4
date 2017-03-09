package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskmanager.model.task.StartDate;

public class PhoneTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(StartDate.isValidPhone("")); // empty string
        assertFalse(StartDate.isValidPhone(" ")); // spaces only
        assertFalse(StartDate.isValidPhone("phone")); // non-numeric
        assertFalse(StartDate.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(StartDate.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(StartDate.isValidPhone("93121534"));
        assertTrue(StartDate.isValidPhone("4")); // short phone numbers
        assertTrue(StartDate.isValidPhone("124293842033123")); // long phone numbers
    }
}
