package seedu.taskmanager.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EndDateTest {

    @Test
    public void isValidEndDate() {
        // blank end date
        assertFalse(EndDate.isValidEndDate(" ")); // spaces only

        // missing parts
        assertFalse(EndDate.isValidEndDate("01/03")); // missing year (YYYY)
        assertFalse(EndDate.isValidEndDate("01/2016")); // missing day (DD)

        // invalid parts
        assertFalse(EndDate.isValidEndDate("46/02/2017")); // invalid day (DD)
        assertFalse(EndDate.isValidEndDate("6/02/2017")); // invalid day (DD)
        assertFalse(EndDate.isValidEndDate("01/25/2017")); // invalid month (MM)
        assertFalse(EndDate.isValidEndDate("01/5/2017")); // invalid month (MM)
        assertFalse(EndDate.isValidEndDate("01/02/17")); // invalid year (YYYY)
        assertFalse(EndDate.isValidEndDate("01 / 02 / 2017")); // spaces in date
        assertFalse(EndDate.isValidEndDate("01,02,2017")); // invalid separator

        // valid end date
        assertTrue(EndDate.isValidEndDate(""));
        assertTrue(EndDate.isValidEndDate("01/02/2017"));
        assertTrue(EndDate.isValidEndDate("15/12/2019"));
    }
}
