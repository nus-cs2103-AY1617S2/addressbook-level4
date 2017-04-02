//@@author A0144813J
package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.model.task.Deadline;

public class DateTest {

    @Test
    public void isValidDate() {
        // invalid date numbers
        assertFalse(Deadline.isValidDate("")); // empty string
        assertFalse(Deadline.isValidDate(" ")); // spaces only
        assertFalse(Deadline.isValidDate("date")); // non-numeric
        assertFalse(Deadline.isValidDate("05.03.2017")); // dots within digits
        assertFalse(Deadline.isValidDate("05\03\2017")); // backward slashes within digits

        // valid date numbers
        assertTrue(Deadline.isValidDate("05032017"));
        assertTrue(Deadline.isValidDate("05/03/2017")); // slashes within digits
        assertTrue(Deadline.isValidDate("05-03-2017")); // dashes within digits

    }
}
