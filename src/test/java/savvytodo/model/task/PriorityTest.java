package savvytodo.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PriorityTest {

    @Test
    public void isValidPriority() {
        // invalid priority
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("priority")); // non-numeric
        assertFalse(Priority.isValidPriority("9011p041")); // alphabets within digits
        assertFalse(Priority.isValidPriority("9312 1534")); // spaces within digits

        // valid priority
        assertTrue(Priority.isValidPriority("low"));
        assertTrue(Priority.isValidPriority("medium"));
        assertTrue(Priority.isValidPriority("high"));
    }
}
