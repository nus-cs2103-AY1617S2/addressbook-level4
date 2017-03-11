package seedu.typed.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateTest {

    @Test
    public void isValidDate() {
        // invalid dates
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("date")); // non-numeric
        assertFalse(Date.isValidDate("9011p041")); // alphabets within digits
        assertFalse(Date.isValidDate("9312 1534")); // spaces within digits

        // valid dates
        assertTrue(Date.isValidDate("11/12/1534"));
        assertTrue(Date.isValidDate("00/00/0000")); // empty dates
        assertTrue(Date.isValidDate("99/87/1231")); // another crazy date
    }
}
