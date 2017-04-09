package seedu.taskmanager.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EndDateTest {

    @Test
    public void isValidEndDate() {
        // blank end date
        assertFalse(EndDate.isValidEndDate(" ")); // spaces only
        assertFalse(EndDate.isValidEndDate(""));

        // valid end date
        assertTrue(EndDate.isValidEndDate("01/02/2017"));
        assertTrue(EndDate.isValidEndDate("15/12/2019"));
        assertTrue(EndDate.isValidEndDate("today"));
    }
}
