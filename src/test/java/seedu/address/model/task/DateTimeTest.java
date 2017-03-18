package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateTimeTest {

    @Test
    public void isValidDeadline() {
        // invalid deadline
        assertFalse(DateTime.isValidDateTime("03/12/2016")); // inputs a date earlier than the current date

        // valid deadline
        assertTrue(DateTime.isValidDateTime("03/12/2217")); // inputs a date later than the current date
    }
}
