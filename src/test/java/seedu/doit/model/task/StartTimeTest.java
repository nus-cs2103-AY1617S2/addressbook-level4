package seedu.doit.model.task;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.doit.model.item.StartTime;

public class StartTimeTest {

    @Test
    public void isStartEndTime() {
        // valid deadline
        assertTrue(StartTime.isValidStartTime("3/20/17 10:21"));
        assertTrue(StartTime.isValidStartTime("4/21/15 15:10"));
        assertTrue(StartTime.isValidStartTime("5/01/16 14:34"));
        assertTrue(StartTime.isValidStartTime("4/01/17 06:20"));
        assertTrue(StartTime.isValidStartTime("3/25/12 04:55"));
        assertTrue(StartTime.isValidStartTime("4/23/17 14:60"));
        assertTrue(StartTime.isValidStartTime("2/21/17 19:20"));
        assertTrue(StartTime.isValidStartTime("3/28/16 12:15"));
    }
}
