package seedu.taskmanager.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StartDateTest {

    @Test
    public void isValidStartDate() {

        // blank end date
        assertFalse(StartDate.isValidStartDate(" ")); // spaces only

        // missing parts
        assertFalse(StartDate.isValidStartDate("01/03")); // missing year (YYYY)
        assertFalse(StartDate.isValidStartDate("01/2016")); // missing day (DD)

        // invalid parts
        assertFalse(StartDate.isValidStartDate("46/02/2017")); // invalid day (DD)
        assertFalse(StartDate.isValidStartDate("6/02/2017")); // invalid day (DD)
        assertFalse(StartDate.isValidStartDate("01/25/2017")); // invalid month (MM)
        assertFalse(StartDate.isValidStartDate("01/5/2017")); // invalid month (MM)
        assertFalse(StartDate.isValidStartDate("01/02/17")); // invalid year (YYYY)
        assertFalse(StartDate.isValidStartDate("01 / 02 / 2017")); // spaces in date
        assertFalse(StartDate.isValidStartDate("01,02,2017")); // invalid separator

        // valid end date
        assertTrue(StartDate.isValidStartDate(""));
        assertTrue(StartDate.isValidStartDate("01/02/2017"));
        assertTrue(StartDate.isValidStartDate("15/12/2019"));
    }
}
