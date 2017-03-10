package seedu.taskmanager.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskmanager.model.task.Deadline;

public class EndTimeTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Deadline.isValidDeadline("")); // empty string
        assertFalse(Deadline.isValidDeadline(" ")); // spaces only
        assertFalse(Deadline.isValidDeadline("phone")); // non-numeric
        assertFalse(Deadline.isValidDeadline("9011p041")); // alphabets within digits
        assertFalse(Deadline.isValidDeadline("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Deadline.isValidDeadline("93121534"));
        assertTrue(Deadline.isValidDeadline("4")); // short phone numbers
        assertTrue(Deadline.isValidDeadline("124293842033123")); // long phone numbers
    }
}
