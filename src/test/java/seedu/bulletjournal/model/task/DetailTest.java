package seedu.bulletjournal.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DetailTest {

    @Test
    public void isValidAddress() {
        // invalid addresses
        assertFalse(BeginDate.isValidAddress("")); // empty string
        assertFalse(BeginDate.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(BeginDate.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(BeginDate.isValidAddress("-")); // one character
        assertTrue(BeginDate.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
