package seedu.onetwodo.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author A0139343E
public class NameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("do assignments")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("do number 1 hardest tutorial")); // alphanumeric characters
        assertTrue(Name.isValidName("Do wOrK")); // with capital letters
        assertTrue(Name.isValidName("YOYOYO today main task is CS2103 tutorial 5")); // long names
    }
}
