package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(Content.isValidName("")); // empty string
        assertFalse(Content.isValidName(" ")); // spaces only
        assertFalse(Content.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Content.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Content.isValidName("peter jack")); // alphabets only
        assertTrue(Content.isValidName("12345")); // numbers only
        assertTrue(Content.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Content.isValidName("Capital Tan")); // with capital letters
        assertTrue(Content.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
