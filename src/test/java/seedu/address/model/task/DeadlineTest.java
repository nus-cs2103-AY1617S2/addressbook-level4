package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class DeadlineTest {
	
	public static Date CURRENT_DATE = new Date();
	
	@Test
    public void isValidDeadline() {
        // invalid name
        assertFalse(Deadline.isValidDeadline(new Date(0))); // inputs a date earlier than the current date
        assertFalse(Deadline.isValidDeadline(CURRENT_DATE)); // inputs the current time

        // valid name
        assertTrue(Deadline.isValidDeadline(new Date(CURRENT_DATE.getTime() + 1000))); // inputs a date later than the current date
    }
}
