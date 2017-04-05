package seedu.whatsleft.model.activity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class ByTimeTest {
    //@@author A0110491U
    @Test
    public void isValidStartTime() {
        //above the threshold of 2400
        assertFalse(ByTime.isValidByTime("2401")); //above 2400

        //invalid start time because of non-digits present
        assertFalse(ByTime.isValidByTime("ab01")); //not all digits
        assertFalse(ByTime.isValidByTime("abcd")); //all alphabets

        //invalid start time wrong number of digits
        assertFalse(ByTime.isValidByTime("54431")); //more than 4
        assertFalse(ByTime.isValidByTime("122")); //less than 4

        //valid StartTime
        assertTrue(ByTime.isValidByTime("0000"));
        assertTrue(ByTime.isValidByTime("1111"));
        assertTrue(ByTime.isValidByTime("1330"));
        assertTrue(ByTime.isValidByTime("2359"));
    }
}
