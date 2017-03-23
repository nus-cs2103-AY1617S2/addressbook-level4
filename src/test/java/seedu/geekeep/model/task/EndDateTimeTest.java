package seedu.geekeep.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.geekeep.model.task.DateTime;

public class EndDateTimeTest {

    @Test
    public void isValidEndDateTime() {
        // invalid end date time
        assertFalse(DateTime.isValidDateTime("")); // empty string
        assertFalse(DateTime.isValidDateTime(" ")); // spaces only

        // valid end date time
        assertTrue(DateTime.isValidDateTime("01-05-17 1630"));
    }
}
