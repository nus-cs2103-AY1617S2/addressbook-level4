package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.task.Date;

public class PhoneTest {

    @Test
    public void isValidDate() {
        // invalid date numbers
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("date")); // non-numeric
        assertFalse(Date.isValidDate("9011p041")); // alphabets within digits
        assertFalse(Date.isValidDate("9312 1534")); // spaces within digits

        // valid date numbers
        assertTrue(Date.isValidDate("93121534"));
        assertTrue(Date.isValidDate("4")); // short date numbers
        assertTrue(Date.isValidDate("124293842033123")); // long date numbers
    }
}
