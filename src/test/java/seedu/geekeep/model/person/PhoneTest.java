package seedu.geekeep.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.geekeep.model.task.EndDateTime;

public class PhoneTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(EndDateTime.isValidDateTime("")); // empty string
        assertFalse(EndDateTime.isValidDateTime(" ")); // spaces only
        assertFalse(EndDateTime.isValidDateTime("phone")); // non-numeric
        assertFalse(EndDateTime.isValidDateTime("9011p041")); // alphabets within digits
        assertFalse(EndDateTime.isValidDateTime("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(EndDateTime.isValidDateTime("93121534"));
        assertTrue(EndDateTime.isValidDateTime("4")); // short phone numbers
        assertTrue(EndDateTime.isValidDateTime("124293842033123")); // long phone numbers
    }
}
