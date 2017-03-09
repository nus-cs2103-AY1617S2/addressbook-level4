package seedu.onetwodo.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.onetwodo.model.task.Time;

public class TimeTest {

    @Test
    public void isValidTime() {
        
        assertTrue(Time.isValidTime(""));
        
        /*
         * // invalid time assertFalse(Time.isValidTime("")); // empty string
         * assertFalse(Time.isValidTime(" ")); // spaces only
         * assertFalse(Time.isValidTime("phone")); // non-numeric
         * assertFalse(Time.isValidTime("9011p041")); // alphabets within digits
         * assertFalse(Time.isValidTime("9312 1534")); // spaces within digits
         * 
         * // valid time assertTrue(Time.isValidTime("93121534"));
         * assertTrue(Time.isValidTime("4")); // short time
         * assertTrue(Time.isValidTime("124293842033123")); // long time
         */
    }
}
