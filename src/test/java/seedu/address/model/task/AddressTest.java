package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AddressTest {

    @Test
    public void isValidAddress() {
        // invalid addresses
        assertFalse(Description.isValidAddress("")); // empty string
        assertFalse(Description.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(Description.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Description.isValidAddress("-")); // one character
        assertTrue(Description.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
