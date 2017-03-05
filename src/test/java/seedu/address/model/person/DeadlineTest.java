package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DeadlineTest {

    @Test
    public void isValidDate() {
        // invalid addresses
        assertFalse(Deadline.isValidDate("")); // empty string
        assertFalse(Deadline.isValidDate(" ")); // spaces only

        // valid addresses
        assertTrue(Deadline.isValidDate("Blk 456, Den Road, #01-355"));
        assertTrue(Deadline.isValidDate("-")); // one character
        assertTrue(Deadline.isValidDate("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
