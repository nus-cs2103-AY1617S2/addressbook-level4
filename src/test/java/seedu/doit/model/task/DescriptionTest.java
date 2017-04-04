package seedu.doit.model.task;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.doit.model.item.Description;

public class DescriptionTest {

    @Test
    public void isValidDescription() {
        // valid tasks
        assertTrue(Description.isValidDescription("")); // empty string
        assertTrue(Description.isValidDescription(" ")); // spaces only
        assertTrue(Description.isValidDescription("Task is extremely hard" + ""));
        assertTrue(Description.isValidDescription("-")); // one character
        assertTrue(Description.isValidDescription("Task is very easy"));
    }
}
