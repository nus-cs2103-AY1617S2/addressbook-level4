//@@author A0105748B
package seedu.bulletjournal.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DueDateTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(DueDate.isValidDueDate("")); // empty string
        assertFalse(DueDate.isValidDueDate(" ")); // spaces only
        assertFalse(DueDate.isValidDueDate(" 3pm")); // starts with spaces

        // valid phone numbers
        assertTrue(DueDate.isValidDueDate("9am"));
        assertTrue(DueDate.isValidDueDate("today")); // short phone numbers
        assertTrue(DueDate.isValidDueDate("last month")); // long phone numbers
    }
}
