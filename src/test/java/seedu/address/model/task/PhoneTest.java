package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class PhoneTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Date.isValidPhone("")); // empty string
        assertFalse(Date.isValidPhone(" ")); // spaces only
        assertFalse(Date.isValidPhone("phone")); // non-numeric
        assertFalse(Date.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Date.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Date.isValidPhone("93121534"));
        assertTrue(Date.isValidPhone("4")); // short phone numbers
        assertTrue(Date.isValidPhone("124293842033123")); // long phone numbers
    }
}
