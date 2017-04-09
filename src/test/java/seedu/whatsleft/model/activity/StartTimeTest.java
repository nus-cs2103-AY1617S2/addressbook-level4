package seedu.whatsleft.model.activity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class StartTimeTest {
    //@@author A0110491U
    @Test
    public void isValidStartTime() {
        //above the threshold of 2400
        assertFalse(StartTime.isValidStartTime("2401")); //above 2400

        //invalid start time because of non-digits present
        assertFalse(StartTime.isValidStartTime("a401")); //not all digits
        assertFalse(StartTime.isValidStartTime("abcd")); //all alphabets

        //invalid start time wrong number of digits
        assertFalse(StartTime.isValidStartTime("12345")); //more than 4
        assertFalse(StartTime.isValidStartTime("123")); //less than 4

        //valid StartTime
        assertTrue(StartTime.isValidStartTime("0000"));
        assertTrue(StartTime.isValidStartTime("1111"));
        assertTrue(StartTime.isValidStartTime("1330"));
        assertTrue(StartTime.isValidStartTime("2359"));

    }
}
