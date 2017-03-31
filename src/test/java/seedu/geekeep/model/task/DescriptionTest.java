package seedu.geekeep.model.task;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DescriptionTest {

    @Test
    public void isValidDescription() {
        // valid locations
        assertTrue(Description.isValidDescription("")); // empty string
        assertTrue(Description.isValidDescription(" ")); // spaces only
        assertTrue(Description.isValidDescription("Blk 456, Den Road, #01-355"));
        assertTrue(Description.isValidDescription("-")); // one character
        assertTrue(Description.isValidDescription("Leng Inc; 123 Market St; San Francisco CA 2349879; USA")); // long location
    }
}
