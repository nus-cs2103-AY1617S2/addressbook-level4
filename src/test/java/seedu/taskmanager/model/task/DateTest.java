package seedu.taskmanager.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskmanager.model.task.StartDate;

public class DateTest {

    // @@author A0141102H
    @Test
    public void isValidDate() {
        // invalid addresses
        assertFalse(StartDate.isValidDate("")); // empty string
        assertFalse(StartDate.isValidDate(" ")); // spaces only

        // valid addresses
        assertTrue(StartDate.isValidDate("thursday"));
        assertTrue(StartDate.isValidDate("tmr")); // short form
        assertTrue(StartDate.isValidDate("03/03/17")); // DD/MM/YY format
    }
}
