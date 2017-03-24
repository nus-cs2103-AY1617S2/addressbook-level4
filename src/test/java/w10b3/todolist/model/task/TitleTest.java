package w10b3.todolist.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TitleTest {

    @Test
    public void isValidTitle() {
        // invalid name
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only
        assertFalse(Title.isValidTitle("^")); // only non-alphanumeric characters
        assertFalse(Title.isValidTitle("????")); //several non-alphanumeric characters

        // valid name
        assertTrue(Title.isValidTitle("debugging")); // alphabets only
        assertTrue(Title.isValidTitle("12345")); // numbers only
        assertTrue(Title.isValidTitle("My-SQL testing")); //first word contain symbols
        assertTrue(Title.isValidTitle("debug for CS2103")); // alphanumeric characters
        assertTrue(Title.isValidTitle("Debugging")); // with capital letters
        assertTrue(Title.isValidTitle("Debugging until the sun rises again at 7am")); // long
                                                                          // names
    }
}
