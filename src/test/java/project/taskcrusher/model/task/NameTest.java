package project.taskcrusher.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import project.taskcrusher.model.shared.Name;

//@@author A0127737X
public class NameTest {

    @Test
    public void isValidName() {

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("task1*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("homework part two")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("submit CS2106 Assignment")); // alphanumeric characters
        assertTrue(Name.isValidName("The Super Task")); // with capital letters
        assertTrue(Name.isValidName("this task name is supposed to be really long")); // long names
    }
}
