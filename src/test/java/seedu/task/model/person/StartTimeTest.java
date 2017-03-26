package seedu.task.model.person;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.util.NattyDateUtil;
import seedu.task.model.task.StartTime;

public class StartTimeTest {

    @Test
    public void isStartTime() {
        // invalid phone numbers
//        assertFalse(StartTime.isValidTime("")); // empty string
//        assertFalse(StartTime.isValidTime(" ")); // spaces only
        // valid dates
        assertTrue(StartTime.isValidTime(NattyDateUtil.parseSingleDate("03/10/1993 0000")));
//        assertTrue(StartTime.isValidTime("4")); // short phone numbers
//        assertTrue(StartTime.isValidTime("124293842033123")); // long phone numbers
    }
}
