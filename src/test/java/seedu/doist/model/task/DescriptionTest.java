package seedu.doist.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DescriptionTest {

    @Test
    public void isValidDescription() {
        // invalid description
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only
        assertFalse(Description.isValidDescription("^")); // only non-alphanumeric characters
        assertFalse(Description.isValidDescription("Buy milk*")); // contains non-alphanumeric characters

        // valid description
        assertTrue(Description.isValidDescription("buy milk")); // alphabets only
        assertTrue(Description.isValidDescription("12345")); // numbers only
        assertTrue(Description.isValidDescription("Buy Milk 3")); // alphanumeric characters
        assertTrue(Description.isValidDescription("Buy Milk")); // with capital letters
        assertTrue(Description.isValidDescription("Buy Milk At the bookstore along the 3rd street")); // long tasks
    }
}
