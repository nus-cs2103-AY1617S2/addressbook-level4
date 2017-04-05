//@@author A0141138N
package seedu.onetwodo.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.onetwodo.commons.exceptions.IllegalValueException;

public class PriorityTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor() throws Exception {
        thrown.expect(IllegalValueException.class);
        Priority p = new Priority("incorrect");
    }

    @Test
    public void isValidPriority() {

        // empty priority
        assertTrue(Priority.isValidPriority(""));

        // valid priority
        assertTrue(Priority.isValidPriority("HIGH"));
        assertTrue(Priority.isValidPriority("MEDIUM"));
        assertTrue(Priority.isValidPriority("LOW"));
        assertTrue(Priority.isValidPriority("H"));
        assertTrue(Priority.isValidPriority("M"));
        assertTrue(Priority.isValidPriority("L"));

        // valid priority
        // case-insensitive
        assertTrue(Priority.isValidPriority("h"));
        assertTrue(Priority.isValidPriority("m"));
        assertTrue(Priority.isValidPriority("l"));
        assertTrue(Priority.isValidPriority("hIgH"));
        assertTrue(Priority.isValidPriority("MEDium"));

        // invalid priority
        assertFalse(Priority.isValidPriority("@@@"));
        assertFalse(Priority.isValidPriority(" "));
        assertFalse(Priority.isValidPriority("-"));
        assertFalse(Priority.isValidPriority("12345"));

        // invalid priority
        // common mistakes
        assertFalse(Priority.isValidPriority("important"));
        assertFalse(Priority.isValidPriority("urgent"));
        assertFalse(Priority.isValidPriority("#1"));
        assertFalse(Priority.isValidPriority("first"));
        assertFalse(Priority.isValidPriority("top"));
    }

}
