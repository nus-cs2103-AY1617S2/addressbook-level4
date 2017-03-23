package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TimeTest {

    @Test
    public void isValidEndTime() {
        // blank EndTime
        assertFalse(Time.isValidTime("")); // empty string
        //assertFalse(EndTime.isValidEndTime(" ")); // spaces only

        // valid Time
        assertTrue(Time.isValidTime("March Fifteenth")); //alphabets
        assertTrue(Time.isValidTime("20170315")); // number
        assertTrue(Time.isValidTime("By end of March")); // alphabets with capital
        assertTrue(Time.isValidTime("March 15 2017")); // numeric and alphabet
                                                           // and domain name
        assertTrue(Time.isValidTime("March 15, 2017")); // mixture of
                                                               // alphanumeric
                                                               // and comma
                                                               // characters
    }
}
