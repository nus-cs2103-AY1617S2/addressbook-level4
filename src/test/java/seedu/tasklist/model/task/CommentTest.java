package seedu.tasklist.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class CommentTest {

    @Test
    public void isValidComment() {
        // invalid addresses
        assertFalse(Comment.isValidComment("")); // empty string
        assertFalse(Comment.isValidComment(" ")); // spaces only

        // valid addresses
        assertTrue(Comment.isValidComment("Find ways to not feel left out"));
        assertTrue(Comment.isValidComment("-")); // one character
        assertTrue(Comment.isValidComment("Find out why jdk is not displaying the correct ver")); // long comment
    }
}
