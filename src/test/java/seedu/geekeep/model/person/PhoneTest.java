package seedu.geekeep.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.geekeep.model.task.EndDateTime;

public class PhoneTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(EndDateTime.isValidPhone("")); // empty string
        assertFalse(EndDateTime.isValidPhone(" ")); // spaces only
        assertFalse(EndDateTime.isValidPhone("phone")); // non-numeric
        assertFalse(EndDateTime.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(EndDateTime.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(EndDateTime.isValidPhone("93121534"));
        assertTrue(EndDateTime.isValidPhone("4")); // short phone numbers
        assertTrue(EndDateTime.isValidPhone("124293842033123")); // long phone numbers
    }
}
