package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NameTest {

    //@@author A0140023E
    @Test
    public void isValidName() {
        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // space only (0x20)
        assertFalse(Name.isValidName("\t")); // tab only
        assertFalse(Name.isValidName("\n")); // new line only
        assertFalse(Name.isValidName("\u000B")); // Vertical tab only
        assertFalse(Name.isValidName("\f")); // form feed only
        assertFalse(Name.isValidName("\r")); // carriage return only
        assertFalse(Name.isValidName("//")); // only slashes
        assertFalse(Name.isValidName("fetch colleague/boss")); // contains forward slashes

        // valid name
        assertTrue(Name.isValidName("assignment")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("2nd draft")); // alphanumeric characters
        assertTrue(Name.isValidName("Software Engineering Exam")); // with capital letters
        assertTrue(Name.isValidName("Super duper the long task")); // long names
        assertTrue(Name.isValidName("#$!$#!$!@#$!@!~@~!")); // tons of random symbols
        assertTrue(Name.isValidName("Whee~~~~")); // alphabets with symbols
        assertTrue(Name.isValidName("Omg!!")); // alphabets with symbols
        assertTrue(Name.isValidName("你好吗?")); // Valid UTF-16 string with symbols
    }
}
