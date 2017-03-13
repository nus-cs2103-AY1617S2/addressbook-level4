package seedu.doit.model.task;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.doit.model.item.EndTime;

public class EndTimeTest {

    @Test
    public void isValidEndTime() {

        // valid deadline
        assertTrue(EndTime.isValidEndTime("5pm"));
        assertTrue(EndTime.isValidEndTime("next thursday"));  // minimal
        assertTrue(EndTime.isValidEndTime("3/21/17"));
        assertTrue(EndTime.isValidEndTime("next monday"));
        assertTrue(EndTime.isValidEndTime("11pm"));
        assertTrue(EndTime.isValidEndTime("friday"));
        assertTrue(EndTime.isValidEndTime("4/20/17"));
        assertTrue(EndTime.isValidEndTime(""));























    }
}
