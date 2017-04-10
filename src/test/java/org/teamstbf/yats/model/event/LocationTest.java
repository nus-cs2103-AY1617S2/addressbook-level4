package org.teamstbf.yats.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.teamstbf.yats.model.item.Location;

public class LocationTest {

    @Test
    public void test() {
        // valid location
        assertTrue(Location.isValidLocation("")); // empty string
        assertTrue(Location.isValidLocation(" ")); // spaces only
        assertTrue(Location.isValidLocation("I have a location that is less than 500 characters"));

        // invalid location
        assertFalse(Location.isValidLocation(new String(new char[51]).replace("\0", "overloaded")));
        assertFalse(Location.isValidLocation(null));
    }

}
