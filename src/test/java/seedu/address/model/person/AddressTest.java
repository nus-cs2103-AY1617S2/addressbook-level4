package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.task.Note;

public class AddressTest {

    @Test
    public void isValidNote() {
        // invalid addresses
        assertFalse(Note.isValidNote("")); // empty string
        assertFalse(Note.isValidNote(" ")); // spaces only

        // valid addresses
        assertTrue(Note.isValidNote("Blk 456, Den Road, #01-355"));
        assertTrue(Note.isValidNote("-")); // one character
        assertTrue(Note.isValidNote("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
