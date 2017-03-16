package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DeadlineTest {

    @Test
    public void isValidDeadline() {
        // blank deadline
        assertTrue(Deadline.isValidDeadline("")); // empty string
        assertFalse(Deadline.isValidDeadline(" ")); // spaces only

        // missing parts
        assertFalse(Deadline.isValidDeadline("12")); // missing local part
        assertFalse(Deadline.isValidDeadline("March")); // missing '@' symbol
        assertFalse(Deadline.isValidDeadline("Sally")); // missing domain name

        // valid deadline
        assertTrue(Deadline.isValidDeadline("17 Mar"));
        assertTrue(Deadline.isValidDeadline("17/03"));
    }
}
