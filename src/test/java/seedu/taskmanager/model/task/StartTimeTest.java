package seedu.taskmanager.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskmanager.model.task.StartTime;

public class StartTimeTest {

    // @@author A0141102H
    @Test
    public void isValidStartTime() {
        // invalid start time
        assertFalse(StartTime.isValidStartTime("")); // empty string
        assertFalse(StartTime.isValidStartTime(" ")); // spaces only
        assertFalse(StartTime.isValidStartTime("00000")); // extra digit in 24hr format
        assertFalse(StartTime.isValidStartTime("2401")); // exceed the 2359 limit
        assertFalse(StartTime.isValidStartTime("2:00")); // time format with ":"
        assertFalse(StartTime.isValidStartTime("2pm")); // am/pm format

        // valid start time
        assertTrue(StartTime.isValidStartTime("1600")); // 24hr format
        assertTrue(StartTime.isValidStartTime("1200"));
        assertTrue(StartTime.isValidStartTime("1400")); 
    }
}
