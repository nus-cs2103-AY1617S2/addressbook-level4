package seedu.task.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;

public class LocationTest {

    @Test
    public void isValidLocation() {
        // invalid locations
        assertFalse(Location.isValidLocation("")); // empty string
        assertFalse(Location.isValidLocation(" ")); // spaces only

        // valid locations
        assertTrue(Location.isValidLocation("Blk 456, Den Road, #01-355"));
        assertTrue(Location.isValidLocation("-")); // one character
        assertTrue(Location.isValidLocation("Leng Inc; 1234 Market St; "
                + "San Francisco CA 2349879; USA")); // long location
    }
    
    
    @Test
    public void remark_Empty_Constructor(){
        Location x = new Location();
        assertEquals(x.value,"");
    }
    
    @Test
    public void testEquals_Symmetric() throws IllegalValueException {
        Location x = new Location("test location");  // equals and hashCode check name field value
        Location y = new Location("test location");
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }
}
