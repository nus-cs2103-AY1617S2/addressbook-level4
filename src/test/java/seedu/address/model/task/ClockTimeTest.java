package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author A0143873Y
public class ClockTimeTest {

    @Test
    public void isValidClockTime() {
        // blank clock time
        assertFalse(ClockTime.isValidClockTime("")); // empty string
        assertFalse(ClockTime.isValidClockTime(" ")); // spaces only

        // missing parts
        assertFalse(ClockTime.isValidClockTime(":00")); // missing hour
        assertFalse(ClockTime.isValidClockTime("1908")); // missing ":"
        assertFalse(ClockTime.isValidClockTime("10:")); // missing minute

        // invalid parts
        assertFalse(ClockTime.isValidClockTime("-:00"));
        assertFalse(ClockTime.isValidClockTime("00:-"));
        assertFalse(ClockTime.isValidClockTime("0 9:00"));
        assertFalse(ClockTime.isValidClockTime("09:0 9"));
        assertFalse(ClockTime.isValidClockTime("09::09"));
        assertFalse(ClockTime.isValidClockTime("09:0:09"));
        assertFalse(ClockTime.isValidClockTime("A0:30")); // with alphabets

        // valid clock Time
        assertTrue(ClockTime.isValidClockTime("00:00"));
        assertTrue(ClockTime.isValidClockTime("23:59")); // minimal
        assertTrue(ClockTime.isValidClockTime("19:59"));
        assertTrue(ClockTime.isValidClockTime("20:00"));

        // invalid clock time
        assertFalse(ClockTime.isValidClockTime("-01:00"));
        assertFalse(ClockTime.isValidClockTime("24:00"));
        assertFalse(ClockTime.isValidClockTime("24:01"));
        assertFalse(ClockTime.isValidClockTime("00:60"));
        assertFalse(ClockTime.isValidClockTime("00:-00"));
    }
}
