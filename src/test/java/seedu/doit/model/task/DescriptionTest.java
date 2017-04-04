package seedu.doit.model.task;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.model.item.Description;

public class DescriptionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValidDescription() {
        // valid tasks
        assertTrue(Description.isValidDescription("")); // empty string
        assertTrue(Description.isValidDescription(" ")); // spaces only
        assertTrue(Description.isValidDescription("Task is extremely hard" + ""));
        assertTrue(Description.isValidDescription("-")); // one character
        assertTrue(Description.isValidDescription("Task is very easy"));
    }

    @Test
    public void invalidDescription_IllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        Description one = new Description(null);
    }
}
