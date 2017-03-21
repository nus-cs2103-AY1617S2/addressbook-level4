package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class DateTimeTest {

    @Test
    public void isValidDateTime() {
        // invalid dateTime
        assertFalse(DateTime.isValidDateTime("fasdf")); // inputs an invalid date string

        // valid dateTime
        assertTrue(DateTime.isValidDateTime("03/12/2217 12:00")); // inputs a valid date string
    }

    @Test
    public void isInCurrentWeek() {
        LocalDateTime now = LocalDateTime.now();

        try {
            // Today should be in current week
            DateTime today = new DateTime(now);

            // Next week is not in current week
            DateTime nextWeek = new DateTime(now.plusDays(7));

            assertTrue(today.isCurrentWeek());
            assertFalse(nextWeek.isCurrentWeek());
        } catch (IllegalValueException e) {
            fail("Exception should not be thrown.");
        }
    }
}
