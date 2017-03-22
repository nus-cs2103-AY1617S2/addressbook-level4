package seedu.taskmanager.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskmanager.model.task.Date;

public class DateTest {

    @Test
    public void isValidDate() {
        // invalid addresses
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only

        // valid addresses
        assertTrue(Date.isValidDate("thursday"));
        assertTrue(Date.isValidDate("tmr")); // short form
        assertTrue(Date.isValidDate("03/03/17")); // DD/MM/YY format
    }
}
