package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StartTimeTest {

    @Test
    public void isValidStartTime() {
        // invalid phone numbers
//        assertFalse(StartTime.isValidTime("")); // empty string
//        assertFalse(StartTime.isValidTime(" ")); // spaces only
        assertFalse(StartTime.isValidTime("phone")); // non-numeric
        assertFalse(StartTime.isValidTime("2359")); // missing Date part
        assertFalse(StartTime.isValidTime("230317")); // missing Time part

        // valid phone numbers
        assertTrue(StartTime.isValidTime("230317 1534"));
//        assertTrue(StartTime.isValidTime("4")); // short phone numbers
//        assertTrue(StartTime.isValidTime("124293842033123")); // long phone numbers
    }
}
