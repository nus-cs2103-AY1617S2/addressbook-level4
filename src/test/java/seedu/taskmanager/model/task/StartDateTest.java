package seedu.taskmanager.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StartDateTest {

    // @@author A0141102H
    @Test
    public void isValidStartDate() {
        // invalid start date
        assertFalse(StartDate.isValidStartDate("")); // empty string
        assertFalse(StartDate.isValidStartDate(" ")); // spaces only
        assertFalse(StartDate.isValidStartDate("030317")); // DDMMYY with no /
        assertFalse(StartDate.isValidStartDate("4th of May 2016")); // Full date
                                                                    // spelling
        assertFalse(StartDate.isValidStartDate("03/05/2017")); // DDMMYYYY
                                                               // format

        // valid start date
        // assertTrue(StartDate.isValidStartDate("thursday")); // full spelling
        // of day
        // assertTrue(StartDate.isValidStartDate("saturday"));
        // assertTrue(StartDate.isValidStartDate("thurs")); // short form
        // assertTrue(StartDate.isValidStartDate("tmr"));
        assertTrue(StartDate.isValidStartDate("03/03/17")); // DD/MM/YY format
        assertTrue(StartDate.isValidStartDate("04/08/15"));
    }
}
