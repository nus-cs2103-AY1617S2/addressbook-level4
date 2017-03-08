package seedu.geekeep.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.geekeep.model.task.EndDateTime;

public class EndDateTimeTest {

    @Test
    public void isValidEndDateTime() {
        // invalid end date time
        assertFalse(EndDateTime.isValidDateTime("")); // empty string
        assertFalse(EndDateTime.isValidDateTime(" ")); // spaces only

        // valid end date time
        assertTrue(EndDateTime.isValidDateTime("2017-04-01T10:16:30"));
    }
}
