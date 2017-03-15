package typetask.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class DateTest {

    @Test
    public void isValidPhone() {
        // invalid date
        assertTrue(DueDate.isValidDate("")); // empty string
        assertFalse(DueDate.isValidDate(" ")); // spaces only
        assertFalse(DueDate.isValidDate("date")); // non-numeric
        assertFalse(DueDate.isValidDate("0208d2017")); // alphabets within digits
        assertFalse(DueDate.isValidDate("2 8 2017")); // spaces within digits
        // valid date

        assertTrue(DueDate.isValidDate("28/01/2017")); //Number start with 0 for month
        assertTrue(DueDate.isValidDate("02/7/1992")); // Number start with 0 for day
        assertTrue(DueDate.isValidDate("12/8/2017")); // Correct Date example
    }
}
