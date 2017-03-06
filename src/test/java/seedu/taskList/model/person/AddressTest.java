package seedu.taskList.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskList.model.task.Comment;

public class AddressTest {

    @Test
    public void isValidAddress() {
        // invalid addresses
        assertFalse(Comment.isValidAddress("")); // empty string
        assertFalse(Comment.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(Comment.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Comment.isValidAddress("-")); // one character
        assertTrue(Comment.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
