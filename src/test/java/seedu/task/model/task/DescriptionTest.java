package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DescriptionTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only
        assertFalse(Description.isValidDescription("^")); // only non-alphanumeric characters
        assertFalse(Description.isValidDescription("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Description.isValidDescription("peter jack")); // alphabets only
        assertTrue(Description.isValidDescription("12345")); // numbers only
        assertTrue(Description.isValidDescription("peter the 2nd")); // alphanumeric characters
        assertTrue(Description.isValidDescription("Capital Tan")); // with capital letters
        assertTrue(Description.isValidDescription("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
