package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EndTimeTest {

    @Test
    public void isValidStartTime() {
        //above the threshold of 2400
        assertFalse(EndTime.isValidEndTime("2400")); //2400 and above
        assertFalse(EndTime.isValidEndTime("3401")); //2400 and above

        //invalid start time because of non-digits present
        assertFalse(EndTime.isValidEndTime("a401")); //not all digits
        assertFalse(EndTime.isValidEndTime("abcd")); //all alphabets

        //invalid start time wrong number of digits
        assertFalse(EndTime.isValidEndTime("12345")); //more than 4
        assertFalse(EndTime.isValidEndTime("123")); //less than 4

        //valid StartTime
        assertTrue(EndTime.isValidEndTime("0000"));
        assertTrue(EndTime.isValidEndTime("1111"));
        assertTrue(EndTime.isValidEndTime("1330"));
        assertTrue(EndTime.isValidEndTime("2359"));

    }
}
