package seedu.tasklist.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("CS2103T*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("do tutorial")); // alphabets only
        assertTrue(Name.isValidName("2103")); // numbers only
        assertTrue(Name.isValidName("CS3245")); // alphanumeric characters
        assertTrue(Name.isValidName("Update Java")); // with capital letters
        assertTrue(Name.isValidName("CS3245 homework 3")); // long names
    }
}
