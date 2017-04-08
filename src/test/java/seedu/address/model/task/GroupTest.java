package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.task.Group;
//@@author A0164032U
public class GroupTest {

    @Test
    public void isValidGroup() {
        // invalid group
        assertFalse(Group.isValidGroup("")); // empty string
        assertFalse(Group.isValidGroup(" ")); // spaces only

        // valid group
        assertTrue(Group.isValidGroup("^")); // only non-alphanumeric characters
        assertTrue(Group.isValidGroup("peter*")); // contains non-alphanumeric characters
        assertTrue(Group.isValidGroup("Final exam")); // alphabets only
        assertTrue(Group.isValidGroup("12345")); // numbers only
        assertTrue(Group.isValidGroup("pratice 4 english")); // alphanumeric characters
        assertTrue(Group.isValidGroup("Team Work")); // with capital letters
        assertTrue(Group.isValidGroup("Try to do the best in the following week")); // long names
    }
}
