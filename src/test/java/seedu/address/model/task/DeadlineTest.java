package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DeadlineTest {

    @Test
    public void isValidDeadline() {
        // invalid deadlines
        assertFalse(Deadline.isValidDeadline("")); // empty string
        assertFalse(Deadline.isValidDeadline(" ")); // spaces only
        assertFalse(Deadline.isValidDeadline("29-Feb-2017")); // date which does not exist
        assertFalse(Deadline.isValidDeadline("31-Apr-2017")); // date which does not exist
        assertFalse(Deadline.isValidDeadline("12 March 2017")); // wrong format
        assertFalse(Deadline.isValidDeadline("12032017")); // wrong format

        // valid deadlines
        assertTrue(Deadline.isValidDeadline("15-Mar-2017"));
    }
}
