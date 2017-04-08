package seedu.doist.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author A0140887W
public class PriorityTest {

    @Test
    public void isValidAfterProcessingPriority() {
        // invalid priority
        assertFalse(isValidAndProcess("")); // empty string
        assertFalse(isValidAndProcess(" ")); // spaces only
        assertFalse(isValidAndProcess("^")); // only non-alphanumeric characters
        assertFalse(isValidAndProcess("Buy milk*")); // contains non-alphanumeric characters
        assertFalse(isValidAndProcess("high")); // words that are not part of criteria
        assertFalse(isValidAndProcess("nor    mal")); // words that are not part of criteria

        // valid priority
        assertTrue(isValidAndProcess("normal"));
        assertTrue(isValidAndProcess("important"));
        assertTrue(isValidAndProcess("very important"));
        assertTrue(isValidAndProcess("Normal")); // with caps
        assertTrue(isValidAndProcess("Very       important")); // with extra in-between spaces
        assertTrue(isValidAndProcess("Very    \n   important")); // with new line
        assertTrue(isValidAndProcess("    \n     Very       important")); // with space and new line in front
        assertTrue(isValidAndProcess("Very       important      \n   ")); // with space and new line at the back
    }

    private boolean isValidAndProcess(String str) {
        return Priority.isValidPriority(Priority.processPriorityString(str));
    }
}
