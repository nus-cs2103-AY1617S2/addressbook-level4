package seedu.doit.model.task;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.doit.model.item.EndTime;

public class EndTimeTest {

    @Test
    public void isValidEndTime() {
        // valid deadline
        assertTrue(EndTime.isValidEndTime("3/20/17 10:21"));
        assertTrue(EndTime.isValidEndTime("4/21/15 15:10"));
        assertTrue(EndTime.isValidEndTime("5/01/16 14:34"));
        assertTrue(EndTime.isValidEndTime("4/01/17 06:20"));
        assertTrue(EndTime.isValidEndTime("3/25/12 04:55"));
        assertTrue(EndTime.isValidEndTime("4/23/17 14:60"));
        assertTrue(EndTime.isValidEndTime("2/21/17 19:20"));
        assertTrue(EndTime.isValidEndTime("3/28/16 12:15"));

    }
}
