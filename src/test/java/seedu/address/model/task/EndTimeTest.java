package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EndTimeTest {

    @Test
    public void isValidEndTime() {
        // blank EndTime
        assertFalse(EndTime.isValidEndTime("")); // empty string
        //assertFalse(EndTime.isValidEndTime(" ")); // spaces only

        // valid EndTime
        assertTrue(EndTime.isValidEndTime("march fifteenth")); //alphabets
        assertTrue(EndTime.isValidEndTime("20170315")); // number
        assertTrue(EndTime.isValidEndTime("By end of March")); // alphabets with capital
        assertTrue(EndTime.isValidEndTime("March 15 2017")); // numeric and alphabet
                                                           // and domain name
        assertTrue(EndTime.isValidEndTime("March 15, 2017")); // mixture of
                                                               // alphanumeric
                                                               // and comma
                                                               // characters
    }
}
