package seedu.geekeep.model.task;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LocationTest {

    @Test
    public void isValidLocation() {
        // valid locations
        assertTrue(Location.isValidLocation("")); // empty string
        assertTrue(Location.isValidLocation(" ")); // spaces only
        assertTrue(Location.isValidLocation("Blk 456, Den Road, #01-355"));
        assertTrue(Location.isValidLocation("-")); // one character
        assertTrue(Location.isValidLocation("Leng Inc; 123 Market St; San Francisco CA 2349879; USA")); // long location
    }
}
