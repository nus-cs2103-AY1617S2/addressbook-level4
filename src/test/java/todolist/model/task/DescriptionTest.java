package todolist.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DescriptionTest {
    @Test
    //@@A0122017Y
    public void isValid() {
        // invalid urgency level
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only
        assertFalse(Description.isValidDescription("^")); // only non-alphanumeric character
        assertFalse(Description.isValidDescription("$%^&aa")); // only alphabetic strings

        // valid urgency level
        assertTrue(Description.isValidDescription("a")); // Description with one small letter
        assertTrue(Description.isValidDescription("A")); // Description with one capital letter
        assertTrue(Description.isValidDescription("0122017")); // Description with only numbers
        // Description with both number, capital letters, and small letters
        assertTrue(Description.isValidDescription("AG0122017Ykkkk"));
        assertTrue(Description.isValidDescription("Hi there")); // Description with letters and spaces
        // Description with letters, spaces and symbols
        assertTrue(Description.isValidDescription("I am not going to fail the test!"));
        assertTrue(Description.isValidDescription("I am not going to fail the test, but I don't want to study, "
                + "but I don't want to fail the test, but then I have to study, "
                + "but I don't want to study")); //Long string
    }
}
