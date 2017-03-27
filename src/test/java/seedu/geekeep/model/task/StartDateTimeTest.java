package seedu.geekeep.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StartDateTimeTest {

    @Test
    public void isValidStartDateTime() {
        // invalid start date time
        assertFalse(DateTime.isValidDateTime("")); // empty string
        assertFalse(DateTime.isValidDateTime(" ")); // spaces only

        // valid start date time
        assertTrue(DateTime.isValidDateTime("01-04-17 1630"));
    }
}
