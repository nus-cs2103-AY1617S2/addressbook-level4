package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.task.Description;

public class NameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(Description.isValidName("")); // empty string
        assertFalse(Description.isValidName(" ")); // spaces only
        assertFalse(Description.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Description.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Description.isValidName("peter jack")); // alphabets only
        assertTrue(Description.isValidName("12345")); // numbers only
        assertTrue(Description.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Description.isValidName("Capital Tan")); // with capital letters
        assertTrue(Description.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
