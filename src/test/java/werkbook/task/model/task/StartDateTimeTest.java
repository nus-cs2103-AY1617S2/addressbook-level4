package werkbook.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import werkbook.task.commons.exceptions.IllegalValueException;

public class StartDateTimeTest {

    @Test
    public void isValidStartDateTime() {
        try {
            // invalid start datetime
            assertFalse(StartDateTime.isValidStartDateTime("")); // empty string
            assertFalse(StartDateTime.isValidStartDateTime(" ")); // spaces only
            assertFalse(StartDateTime.isValidStartDateTime("120315 12:35PM")); // wrong datetime format
            assertFalse(StartDateTime.isValidStartDateTime("11/13/2017 2530")); // invalid datetime

            // valid start datetime
            assertTrue(StartDateTime.isValidStartDateTime("03/03/2017 2230")); // valid datetime format
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
    }
}
