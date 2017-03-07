package seedu.tasklist.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.tasklist.model.task.Comment;

public class CommentTest {

    @Test
    public void isValidComment() {
        // invalid addresses
        assertFalse(Comment.isValidComment("")); // empty string
        assertFalse(Comment.isValidComment(" ")); // spaces only

        // valid addresses
        assertTrue(Comment.isValidComment("Blk 456, Den Road, #01-355"));
        assertTrue(Comment.isValidComment("-")); // one character
        assertTrue(Comment.isValidComment("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
