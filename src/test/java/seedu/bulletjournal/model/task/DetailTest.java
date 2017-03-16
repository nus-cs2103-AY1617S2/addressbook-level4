package seedu.bulletjournal.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DetailTest {

    @Test
    public void isValidAddress() {
        // invalid addresses
        assertFalse(StartTime.isValidAddress("")); // empty string
        assertFalse(StartTime.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(StartTime.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(StartTime.isValidAddress("-")); // one character
        assertTrue(StartTime.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
