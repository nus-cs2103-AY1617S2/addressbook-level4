package seedu.bulletjournal.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DetailTest {

    @Test
    public void isValidAddress() {
        // invalid addresses
        assertFalse(StartDate.isValidAddress("")); // empty string
        assertFalse(StartDate.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(StartDate.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(StartDate.isValidAddress("-")); // one character
        assertTrue(StartDate.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
