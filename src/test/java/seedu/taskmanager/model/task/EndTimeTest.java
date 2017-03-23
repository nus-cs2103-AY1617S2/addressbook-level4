package seedu.taskmanager.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskmanager.model.task.EndTime;

public class EndTimeTest {

    @Test
    public void isValidEndTime() {
        // invalid phone numbers
        assertFalse(EndTime.isValidEndTime("")); // empty string
        assertFalse(EndTime.isValidEndTime(" ")); // spaces only
        assertFalse(EndTime.isValidEndTime("9011p041")); // alphabets within digits
        assertFalse(EndTime.isValidEndTime("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(EndTime.isValidEndTime("1600"));
        assertTrue(EndTime.isValidEndTime("1200"));
        assertTrue(EndTime.isValidEndTime("03/03/17 1400")); // long phone numbers
    }
}
