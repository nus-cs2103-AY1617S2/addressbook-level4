package seedu.whatsleft.model.activity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


//@@author A0148038A

public class LocationTest {

    @Test
    public void isValidLocation() {
        // invalid location
        assertFalse(Location.isValidLocation(" ")); // spaces only
        assertFalse(Location.isValidLocation("")); //empty

        // valid location
        assertTrue(Location.isValidLocation("NUS"));
        assertTrue(Location.isValidLocation("-")); // one character
        assertTrue(Location.isValidLocation("Central Library, NUS, Singapore")); // long location
    }
}
