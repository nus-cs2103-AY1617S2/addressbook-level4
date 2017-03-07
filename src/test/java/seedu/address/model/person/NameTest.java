package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(Content.isValidDate("")); // empty string
        assertFalse(Content.isValidDate(" ")); // spaces only
        assertFalse(Content.isValidDate("^")); // only non-alphanumeric characters
        assertFalse(Content.isValidDate("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Content.isValidDate("peter jack")); // alphabets only
        assertTrue(Content.isValidDate("12345")); // numbers only
        assertTrue(Content.isValidDate("peter the 2nd")); // alphanumeric characters
        assertTrue(Content.isValidDate("Capital Tan")); // with capital letters
        assertTrue(Content.isValidDate("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
