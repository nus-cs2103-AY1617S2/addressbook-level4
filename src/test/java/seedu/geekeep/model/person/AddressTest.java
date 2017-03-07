package seedu.geekeep.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.geekeep.model.task.Location;

public class AddressTest {

    @Test
    public void isValidAddress() {
        // invalid addresses
        assertFalse(Location.isValidAddress("")); // empty string
        assertFalse(Location.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(Location.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Location.isValidAddress("-")); // one character
        assertTrue(Location.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
