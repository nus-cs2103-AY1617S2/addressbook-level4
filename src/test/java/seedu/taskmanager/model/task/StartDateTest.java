package seedu.taskmanager.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StartDateTest {

    @Test
    public void isValidStartDate() {

        // @@author A0140032E
        // blank end date
        assertFalse(StartDate.isValidStartDate(" ")); // spaces only

        assertFalse(StartDate.isValidStartDate("")); // blank

        // invalid parts

        // valid end date
        assertTrue(StartDate.isValidStartDate("01/02/2017"));
        assertTrue(StartDate.isValidStartDate("15/12/2019"));
        assertTrue(StartDate.isValidStartDate("01/03")); //relative date
    }
}
