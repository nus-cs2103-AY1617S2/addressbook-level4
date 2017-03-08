package seedu.geekeep.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.geekeep.model.task.StartDateTime;

public class StartDateTimeTest {

    @Test
    public void isValidStartDateTime() {
        // invalid start date time
        assertFalse(StartDateTime.isValidDateTime("")); // empty string
        assertFalse(StartDateTime.isValidDateTime(" ")); // spaces only

        // valid start date time
        assertTrue(StartDateTime.isValidDateTime("2017-04-01T10:16:30"));
    }
}
