package seedu.task.model.person;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.util.NattyDateUtil;
import seedu.task.model.task.StartTime;

public class StartTimeTest {

    @Test
    public void isStartTime() {
        // blank StartTime
        assertTrue(StartTime.isValidTime(null)); // empty string

        // valid StartTime
        assertTrue(StartTime.isValidTime(NattyDateUtil.parseSingleDate("03/10/1993 0000")));
    }
}
