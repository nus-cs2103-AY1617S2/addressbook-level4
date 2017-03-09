package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.task.Time;

public class PhoneTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Time.isValidPhone("")); // empty string
        assertFalse(Time.isValidPhone(" ")); // spaces only
        assertFalse(Time.isValidPhone("phone")); // non-numeric
        assertFalse(Time.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Time.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Time.isValidPhone("93121534"));
        assertTrue(Time.isValidPhone("4")); // short phone numbers
        assertTrue(Time.isValidPhone("124293842033123")); // long phone numbers
    }
}
