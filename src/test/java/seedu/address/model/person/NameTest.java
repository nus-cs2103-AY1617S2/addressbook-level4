package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(Name.isValidTaskName("")); // empty string
        assertFalse(Name.isValidTaskName(" ")); // spaces only
        assertFalse(Name.isValidTaskName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidTaskName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidTaskName("peter jack")); // alphabets only
        assertTrue(Name.isValidTaskName("12345")); // numbers only
        assertTrue(Name.isValidTaskName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidTaskName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidTaskName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
