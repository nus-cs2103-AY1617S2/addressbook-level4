package seedu.watodo.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.watodo.model.task.DateTime;

public class DateTimeTest {

    @Test
    public void isValidDateTime() {
        // invalid dateTime
        assertFalse(DateTime.isValidDateTime("")); // empty string
        assertFalse(DateTime.isValidDateTime(" ")); // spaces only
        assertFalse(DateTime.isValidDateTime("Wed Thurs")); // more than one date
        assertFalse(DateTime.isValidDateTime("31/4"));      // dd/mm instead of mm/dd
        // valid dateTime
        assertTrue(DateTime.isValidDateTime("today"));
        assertTrue(DateTime.isValidDateTime("tmr")); // shortforms
        assertTrue(DateTime.isValidDateTime("Monday")); // days
        assertTrue(DateTime.isValidDateTime("3/4/17")); // dates
        assertTrue(DateTime.isValidDateTime("5.13 pm")); // time
        assertTrue(DateTime.isValidDateTime("17 hrs")); // 24-hr clock format
        assertTrue(DateTime.isValidDateTime("Friday at 8 am")); //date and time combined
        assertTrue(DateTime.isValidDateTime("09 51 3/15")); //time and date combined
    }
}
