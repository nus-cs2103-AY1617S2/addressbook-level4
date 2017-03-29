package seedu.taskmanager.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskmanager.model.task.EndTime;

public class EndTimeTest {

    // @@author A0141102H
    @Test
    public void isValidEndTime() {
        // invalid end time
        assertFalse(EndTime.isValidEndTime("")); // empty string
        assertFalse(EndTime.isValidEndTime(" ")); // spaces only
        assertFalse(EndTime.isValidEndTime("00000")); // extra digit in 24hr format
        assertFalse(EndTime.isValidEndTime("2401")); // exceed the 2359 limit
        assertFalse(EndTime.isValidEndTime("2:00")); // time format with ":"
        assertFalse(EndTime.isValidEndTime("2pm")); // am/pm format

        // valid end time
        assertTrue(EndTime.isValidEndTime("1600")); // 24hr format
        assertTrue(EndTime.isValidEndTime("1200"));
        assertTrue(EndTime.isValidEndTime("1400")); 
    }
}
