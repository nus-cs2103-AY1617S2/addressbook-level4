package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.model.task.EndDateTime;

public class AddressTest {

    @Test
    public void isValidAddress() {
        // invalid addresses
        assertFalse(EndDateTime.isValidAddress("")); // empty string
        assertFalse(EndDateTime.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(EndDateTime.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(EndDateTime.isValidAddress("-")); // one character
        assertTrue(EndDateTime.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
