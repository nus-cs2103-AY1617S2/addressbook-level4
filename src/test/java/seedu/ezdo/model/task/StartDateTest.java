package seedu.ezdo.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ezdo.model.todo.StartDate;

public class StartDateTest {

    @Test
    public void isValidStartDate() {
        // invalid dates
        assertFalse(StartDate.isValidTaskDate(" ")); // spaces only
        assertFalse(StartDate.isValidTaskDate("next")); // non-numeric
        assertFalse(StartDate.isValidTaskDate("13 12 1999")); // spaces within digits
        assertFalse(StartDate.isValidTaskDate("05.10.1977")); // fullstops within digits
        assertFalse(StartDate.isValidTaskDate("22/11/q2r1")); // alphabets within digits

        // valid dates
        assertTrue(StartDate.isValidTaskDate("15/12/1992 10:12")); // month with 31 days
        assertTrue(StartDate.isValidTaskDate("11/02/2014 07:21")); // month with 30 days
        assertTrue(StartDate.isValidTaskDate("29/02/2003 20:21")); // leap year
    }
}