package seedu.whatsleft.model.activity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


//@@author A0148038A

public class LocationTest {

    @Test
    public void isValidLocation() {
        // invalid location
        assertFalse(Location.isValidLocation(" ")); // spaces only
        assertFalse(Location.isValidLocation("")); //empty

        // valid location
        assertTrue(Location.isValidLocation("Blk 456, Den Road, #01-355"));
        assertTrue(Location.isValidLocation("-")); // one character
        assertTrue(Location.isValidLocation("Leng Inc; 123 Market St; San Francisco CA 2349879; USA")); // long location
    }
}
