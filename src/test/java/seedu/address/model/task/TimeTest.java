package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TimeTest {

    @Test
    public void isValidTime() {
        // invalid phone numbers
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only
        assertFalse(Time.isValidTime("phone")); // non-numeric
        assertFalse(Time.isValidTime("9011p041")); // alphabets within digits
        assertFalse(Time.isValidTime("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Time.isValidTime("09/08/2017"));
        assertTrue(Time.isValidTime("11/12/2017"));
        assertTrue(Time.isValidTime("18/03/2018"));
    }
}
