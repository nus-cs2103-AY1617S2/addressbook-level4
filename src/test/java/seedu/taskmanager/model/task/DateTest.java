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
        assertTrue(Date.isValidDate("Blk 456, Den Road, #01-355"));
        assertTrue(Date.isValidDate("-")); // one character
        assertTrue(Date.isValidDate("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
