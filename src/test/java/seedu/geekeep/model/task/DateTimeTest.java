//@@author A0139438W
package seedu.geekeep.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateTimeTest {

    @Test
    public void isValidDateTime() {
        // invalid start date time
        assertFalse(DateTime.isValidDateTime("")); // empty string
        assertFalse(DateTime.isValidDateTime(" ")); // spaces only
        assertFalse(DateTime.isValidDateTime("01-04-171630")); // missing space
        assertFalse(DateTime.isValidDateTime("1-4-17 1630")); // missing padded zeroes
        assertFalse(DateTime.isValidDateTime("1630")); // only time without date

        // valid start date time
        assertTrue(DateTime.isValidDateTime("01-04-17 1630"));
        assertTrue(DateTime.isValidDateTime("01-04-17"));
    }

}
