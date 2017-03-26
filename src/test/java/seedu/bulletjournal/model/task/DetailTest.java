package seedu.bulletjournal.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DetailTest {

    @Test
    public void isValidBeginDate() {
        // invalid addresses
        assertFalse(BeginDate.isValidBeginDate("")); // empty string
        assertFalse(BeginDate.isValidBeginDate(" ")); // spaces only

        // valid addresses
        assertTrue(BeginDate.isValidBeginDate("Blk 456, Den Road, #01-355"));
        assertTrue(BeginDate.isValidBeginDate("-")); // one character
        assertTrue(BeginDate.isValidBeginDate("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
