package seedu.address.model.task;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DeadlineTest {

    @Test
    public void isValidDate() {
        // invalid deadlines


        // valid deadlines
        assertTrue(Deadline.isValidDeadline("11-12-2106")); // date
        assertTrue(Deadline.isValidDeadline("Tomorrow"));
        assertTrue(Deadline.isValidDeadline("9pm")); // two characters only
        assertTrue(Deadline.isValidDeadline("")); // empty string
        assertTrue(Deadline.isValidDeadline(" ")); // spaces only
    }
}
