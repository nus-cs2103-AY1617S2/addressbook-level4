package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PhoneTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Phone.isValidDeadline("")); // empty string
        assertFalse(Phone.isValidDeadline(" ")); // spaces only
        assertFalse(Phone.isValidDeadline("phone")); // non-numeric
        assertFalse(Phone.isValidDeadline("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidDeadline("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Phone.isValidDeadline("93121534"));
        assertTrue(Phone.isValidDeadline("4")); // short phone numbers
        assertTrue(Phone.isValidDeadline("124293842033123")); // long phone numbers
    }
}
