package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author ryuus
 *
 */
public class DateTest {

    @Test
    public void isValidDate() {
        // invalid date numbers
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("date")); // non-numeric
        assertFalse(Date.isValidDate("March05")); // alphabets within digits
        assertFalse(Date.isValidDate("05 03 2017")); // spaces within digits
        assertFalse(Date.isValidDate("05-03-2017")); // dashes within digits

        // valid date numbers
        assertTrue(Date.isValidDate("05032017"));
        assertTrue(Date.isValidDate("5317")); // short phone numbers
    }
}
