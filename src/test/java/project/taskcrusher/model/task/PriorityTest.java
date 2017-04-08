package project.taskcrusher.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import project.taskcrusher.model.shared.Priority;

//@@author A0127737X
public class PriorityTest {

    @Test
    public void isValidPriority() {
        // invalid priority
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("ps")); // non-numeric
        assertFalse(Priority.isValidPriority("9011p041")); // alphabets within digits
        assertFalse(Priority.isValidPriority("4")); // out of range

        // valid priority
        assertTrue(Priority.isValidPriority("1"));
        assertTrue(Priority.isValidPriority("2"));
        assertTrue(Priority.isValidPriority("3"));
    }

    @Test
    public void comparePriority() throws Exception {
        Priority one = new Priority("1");
        Priority two = new Priority("2");
        Priority three = new Priority("3");
        assertTrue(two.compareTo(one) < 0);
        assertTrue(one.compareTo(three) > 0);
    }
}
