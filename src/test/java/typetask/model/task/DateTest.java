package typetask.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import typetask.model.task.Date;


public class DateTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("date")); // non-numeric
        assertFalse(Date.isValidDate("0208d2017")); // alphabets within digits
        assertFalse(Date.isValidDate("2 8 2017")); // spaces within digits

        // valid phone numbers
        assertTrue(Date.isValidDate("282017"));
        assertTrue(Date.isValidDate("0207")); // short phone numbers
        assertTrue(Date.isValidDate("02082017")); // long phone numbers
    }
}
