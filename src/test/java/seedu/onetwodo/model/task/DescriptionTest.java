package seedu.onetwodo.model.task;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

//author A0139343E
public class DescriptionTest {

    @Test
    public void isValidDescription() {
        assertTrue(Description.isValidDescription(""));

        // valid description
        assertTrue(Description.isValidDescription("this module so harddddddd"));
        assertTrue(Description.isValidDescription("-")); // one character
        assertTrue(Description.isValidDescription("Task difficulty level: 9000, effort required: 99999,"
                + " rest period available: NULL"));  // long description
        assertTrue(Description.isValidDescription("12345+_=-~`| ~!@#$%^&*()[];',./{}:")); // special input
    }
}
