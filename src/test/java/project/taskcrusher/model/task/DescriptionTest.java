package project.taskcrusher.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.model.shared.Description;

//@@author A0127737X
public class DescriptionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValidDescription() {
        // invalid descriptions
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid descriptions
        assertTrue(Description.isValidDescription("To kill a mocking bird"));
        assertTrue(Description.isValidDescription("-")); // one character

        // long description
        assertTrue(Description.isValidDescription("This should be done after I actually sleep enough tonight"));
    }

    @Test
    public void constructorInValidDescription() throws Exception {
        thrown.expect(IllegalValueException.class);
        Description invalidDescription = new Description(" ");
    }

    @Test
    public void descriptionTestEqual() throws Exception {
        String sampleDescription = "this is just a description";
        String sampleDescriptionCopy = "this is just a description";
        Description desc1 = new Description(sampleDescription);
        Description desc2 = new Description(sampleDescriptionCopy);
        assertEquals(desc1, desc2);
    }
}
