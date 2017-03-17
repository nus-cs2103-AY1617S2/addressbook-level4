package seedu.address.model.person;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DeadlineTest {

    @Test
    public void isValidDeadline() {
        // blank deadline
        assertTrue(Deadline.isValidDeadline("")); // empty string
        // valid deadline
        assertTrue(Deadline.isValidDeadline("17 Mar"));
        assertTrue(Deadline.isValidDeadline("3 am"));
        assertTrue(Deadline.isValidDeadline("tomorrow"));
    }
}
