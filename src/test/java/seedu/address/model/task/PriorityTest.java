package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author ryuus
 *
 */
public class PriorityTest {

    @Test
    public void isValidPriority() {
        // blank priorities
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only

        // invalid priorities
        assertFalse(Priority.isValidPriority("^^"));
        assertFalse(Priority.isValidPriority("@##@#"));
        assertFalse(Priority.isValidPriority("{{{{{{{{"));

        // valid priorities
        assertTrue(Priority.isValidPriority("urgent"));
        assertTrue(Priority.isValidPriority("very_urgent"));
        assertTrue(Priority.isValidPriority("very..urgent"));
        assertTrue(Priority.isValidPriority("urgentLevel2"));
        assertTrue(Priority.isValidPriority("urgent..."));
        assertTrue(Priority.isValidPriority("very.urgent"));
        assertTrue(Priority.isValidPriority("so_urgent_that_i_can_t_even_start"));
        assertTrue(Priority.isValidPriority("so.urgent.that.i.don.t.have.to.start.now."));
    }
}
