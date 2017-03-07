package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DeadlineTest {

    @Test
    public void isValidDate() {
        // invalid deadlines
        assertFalse(Deadline.isValidDate("")); // empty string
        assertFalse(Deadline.isValidDate(" ")); // spaces only

        // valid deadlines
        assertTrue(Deadline.isValidDate("11-12-2106")); // date
        assertTrue(Deadline.isValidDate("Tomorrow"));
        assertTrue(Deadline.isValidDate("9pm")); // two characters only
    }
}
