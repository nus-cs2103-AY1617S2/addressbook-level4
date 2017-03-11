package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TaskDateTimeTest {

    @Test
    public void isValidDateTime() {
        // invalid phone numbers
        assertFalse(TaskDateTime.isValidDateTime("")); // empty string
        assertFalse(TaskDateTime.isValidDateTime(" ")); // spaces only
        assertFalse(TaskDateTime.isValidDateTime("dateTime")); // non-numeric
        assertFalse(TaskDateTime.isValidDateTime("12")); // not a date
        assertFalse(TaskDateTime.isValidDateTime("1/1 11:12")); // no year
        assertFalse(TaskDateTime.isValidDateTime("9/2017 22:33")); //no day
        assertFalse(TaskDateTime.isValidDateTime("3/22/2017 11:00")); //invalid month
        assertFalse(TaskDateTime.isValidDateTime("32/22/2017 11:00")); //invalid day
        assertFalse(TaskDateTime.isValidDateTime("3/22/2017 23:66")); //invalid time
        assertFalse(TaskDateTime.isValidDateTime("29/2/2017 23:00")); //invalid date

        // valid phone numbers
        assertTrue(TaskDateTime.isValidDateTime("01/02/03 04:05"));
        assertTrue(TaskDateTime.isValidDateTime("11/12/2017 00:00")); // normal date
        assertTrue(TaskDateTime.isValidDateTime("29/02/2016 1:00")); // leap year
    }
}
