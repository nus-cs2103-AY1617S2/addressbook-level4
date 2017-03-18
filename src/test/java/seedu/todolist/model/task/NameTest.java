package seedu.todolist.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NameTest {

    @Test
    public void isValidName() {
        // invalid task name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("laundry*")); // contains non-alphanumeric characters

        // valid task name
        assertTrue(Name.isValidName("buy milk")); // alphabets only
        assertTrue(Name.isValidName("20170727")); // numbers only
        assertTrue(Name.isValidName("buy 2 loaves of bread")); // alphanumeric characters
        assertTrue(Name.isValidName("send email to Bill Cosby")); // with capital letters
        assertTrue(Name.isValidName("Pick up dinner and some fruits before going home")); // long task names
    }
}
