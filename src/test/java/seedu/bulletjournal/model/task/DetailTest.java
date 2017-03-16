package seedu.bulletjournal.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DetailTest {

    @Test
    public void isValidAddress() {
        // invalid addresses
        assertFalse(BeginTime.isValidAddress("")); // empty string
        assertFalse(BeginTime.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(BeginTime.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(BeginTime.isValidAddress("-")); // one character
        assertTrue(BeginTime.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
