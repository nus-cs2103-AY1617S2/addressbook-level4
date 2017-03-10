package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateTest {

    @Test
    public void isValidDate() {
        // invalid dates
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("phone")); // non-numeric
        assertFalse(Date.isValidDate("9011p041")); // alphabets within digits
        assertFalse(Date.isValidDate("9312 1534")); // spaces within digits
        assertFalse(Date.isValidDate("00-00-0000")); // invalid record
        assertFalse(Date.isValidDate("16-16-1993")); // invalid month

        // valid dates
        assertTrue(Date.isValidDate("23-12-1934"));
        assertTrue(Date.isValidDate("03-01-1000"));
        assertTrue(Date.isValidDate("31-12-2999"));
    }
}
