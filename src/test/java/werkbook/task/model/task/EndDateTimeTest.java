package werkbook.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EndDateTimeTest {

    @Test
    public void isValidEndDateTime() {
        // invalid end datetime
        assertFalse(EndDateTime.isValidEndDateTime("")); // empty string
        assertFalse(EndDateTime.isValidEndDateTime(" ")); // spaces only
        assertFalse(EndDateTime.isValidEndDateTime("120315 12:35PM")); // wrong datetime format
        assertFalse(EndDateTime.isValidEndDateTime("11/13/2017 2530")); // invalid datetime

        // valid end datetime
        assertTrue(EndDateTime.isValidEndDateTime("03/03/2017 2230")); // valid datetime format
    }
}
