package savvytodo.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LocationTest {

    @Test
    public void isValidLocation() {
        // invalid addresses
        assertFalse(Location.isValidLocation("")); // empty string
        assertFalse(Location.isValidLocation(" ")); // spaces only

        // valid locations
        assertTrue(Location.isValidLocation("Blk 456, Den Road, #01-355"));
        assertTrue(Location.isValidLocation("-")); // one character
        assertTrue(Location.isValidLocation("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
