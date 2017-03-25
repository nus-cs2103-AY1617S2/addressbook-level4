package project.taskcrusher.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import project.taskcrusher.model.shared.Description;

public class DescriptionTest {

    @Test
    public void isValidDescription() {
        // invalid descriptions
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid descriptions
        assertTrue(Description.isValidDescription("To kill a mocking bird"));
        assertTrue(Description.isValidDescription("-")); // one character
        assertTrue(Description.isValidDescription("This should be done after I actually sleep enough")); // long
                                                                                                         // description
    }
}
