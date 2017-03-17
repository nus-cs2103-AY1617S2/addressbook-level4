package seedu.jobs.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.jobs.model.task.Description;

public class DescriptionTest {

    @Test
    public void isValidTime() {
        // valid description
    	
        assertTrue(Description.isValidDescription("")); // empty string
        assertTrue(Description.isValidDescription(" ")); // spaces only
        assertTrue(Description.isValidDescription(ModelConstant.getLongString(149)));

        // invalid descriptions (long characters)
        assertFalse(Description.isValidDescription(ModelConstant.getLongString(150)));
        assertFalse(Description.isValidDescription(ModelConstant.getLongString(151))); 
    }
}
