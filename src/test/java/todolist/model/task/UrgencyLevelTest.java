package todolist.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UrgencyLevelTest {
    @Test
    //@@A0122017Y
    public void isValid() {
        // invalid urgency level
        assertFalse(UrgencyLevel.isValidUrgencyLevel("")); // empty string
        assertFalse(UrgencyLevel.isValidUrgencyLevel(" ")); // spaces only
        assertFalse(UrgencyLevel.isValidUrgencyLevel("^")); // only non-alphanumeric character
        assertFalse(UrgencyLevel.isValidUrgencyLevel("aa")); // only alphabetic strings
        assertFalse(UrgencyLevel.isValidUrgencyLevel("1*")); // number with non-alphanumeric character
        assertFalse(UrgencyLevel.isValidUrgencyLevel("6")); // number not in the range 1-5

        // valid urgency level
        assertTrue(UrgencyLevel.isValidUrgencyLevel("1")); // UrgencyLevel at 1
        assertTrue(UrgencyLevel.isValidUrgencyLevel("2")); // UrgencyLevel at 2
        assertTrue(UrgencyLevel.isValidUrgencyLevel("3")); // UrgencyLevel at 3
        assertTrue(UrgencyLevel.isValidUrgencyLevel("4")); // UrgencyLevel at 4
        assertTrue(UrgencyLevel.isValidUrgencyLevel("5")); // UrgencyLevel at 5
    }
}
