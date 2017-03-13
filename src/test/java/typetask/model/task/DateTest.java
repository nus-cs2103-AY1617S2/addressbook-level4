package typetask.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class DateTest {

    @Test
    public void isValidPhone() {
        // invalid date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("date")); // non-numeric
        assertFalse(Date.isValidDate("0208d2017")); // alphabets within digits
        assertFalse(Date.isValidDate("2 8 2017")); // spaces within digits
        // valid date

        assertTrue(Date.isValidDate("28/01/2017")); //Number start with 0 for month
        assertTrue(Date.isValidDate("02/7/1992")); // Number start with 0 for day
        assertTrue(Date.isValidDate("12/8/2017")); // Correct Date example
    }
}
