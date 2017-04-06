package seedu.taskmanager.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EndDateTest {

    // @@author A0141102H
    @Test
    public void isValidEndDate() {
        // invalid end date
        assertFalse(EndDate.isValidEndDate("")); // empty string
        assertFalse(EndDate.isValidEndDate(" ")); // spaces only
        assertFalse(EndDate.isValidEndDate("030317")); // DDMMYY with no /
        assertFalse(EndDate.isValidEndDate("4th of May 2016")); // Full date
                                                                // spelling
        assertFalse(EndDate.isValidEndDate("03/05/2017")); // DDMMYYYY format

        // valid end date
        // assertTrue(EndDate.isValidEndDate("thursday")); // full spelling of
        // day
        // assertTrue(EndDate.isValidEndDate("saturday"));
        // assertTrue(EndDate.isValidEndDate("thurs")); // short form
        // assertTrue(EndDate.isValidEndDate("tmr"));
        assertTrue(EndDate.isValidEndDate("03/03/17")); // DD/MM/YY format
        assertTrue(EndDate.isValidEndDate("04/08/15"));
    }
}
