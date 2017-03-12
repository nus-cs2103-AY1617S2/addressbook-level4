package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DescriptionTest {

    @Test
    public void isValidDescription() {
        // invalid description
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid description
        assertTrue(Description.isValidDescription("school test")); // alphabets only
        assertTrue(Description.isValidDescription("12345")); // numbers only
        assertTrue(Description.isValidDescription("cs2103 2nd test")); // alphanumeric characters
        assertTrue(Description.isValidDescription("CS2103 Test")); // with capital letters
        assertTrue(Description.isValidDescription("First exam of the CS2105")); // long names
    }
}
