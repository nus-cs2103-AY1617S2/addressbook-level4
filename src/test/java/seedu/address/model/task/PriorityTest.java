package seedu.address.model.task;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PriorityTest {

    @Test
    public void isValidPriority() {
        // valid priorities
        assertTrue(Priority.isValidPriority("93121534"));
        assertTrue(Priority.isValidPriority("4"));
        assertTrue(Priority.isValidPriority("124293842033123"));
    }
}
