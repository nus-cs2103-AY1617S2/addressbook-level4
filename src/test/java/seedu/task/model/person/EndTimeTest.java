package seedu.task.model.person;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.util.NattyDateUtil;
import seedu.task.model.task.EndTime;

public class EndTimeTest {

    @Test
    public void isEndTime() {
        // blank EndTime
        assertTrue(EndTime.isValidTime(null)); // empty string

        // valid EndTime
        assertTrue(EndTime.isValidTime(NattyDateUtil.parseSingleDate("03/10/1993 0000")));
    }
}
