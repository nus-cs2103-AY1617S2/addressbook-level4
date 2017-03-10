package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EndTimeTest {

    @Test
    public void isValidEndTime() {
        // invalid addresses
        assertFalse(EndTime.isValidEndTime("")); // empty string
        //assertFalse(EndTime.isValidEndTime(" ")); // spaces only

        // valid addresses
        assertTrue(EndTime.isValidEndTime("Blk 456, Den Road, #01-355"));
        assertTrue(EndTime.isValidEndTime("-")); // one character
        assertTrue(EndTime.isValidEndTime("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long
                                                                                                       // address
    }
}
