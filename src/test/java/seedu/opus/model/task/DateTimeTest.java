package seedu.opus.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.Test;

import seedu.opus.commons.exceptions.IllegalValueException;

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
        Clock fixedClock = Clock.fixed(now.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

        try {
            // Today should be in current week
            DateTime today = new DateTime(now);
            today.setClock(fixedClock);
            assertTrue(today.isCurrentWeek());

            // Next week is not in current week
            DateTime nextWeek = new DateTime(now.plusDays(7));
            nextWeek.setClock(fixedClock);
            assertFalse(nextWeek.isCurrentWeek());

            // This Sunday should be in current week
            int daysToSunday = 7 - now.getDayOfWeek().getValue() - 1;
            DateTime sunday = new DateTime(now.plusDays(daysToSunday));
            sunday.setClock(fixedClock);
            assertTrue(sunday.isCurrentWeek());

            // Next Monday should not be in current week
            DateTime nextMonday = new DateTime(now.plusDays(daysToSunday + 1));
            nextMonday.setClock(fixedClock);
            assertFalse(nextMonday.isCurrentWeek());

            // Last Sunday should not be in current week
            DateTime lastSunday = new DateTime(now.minusDays(now.getDayOfWeek().getValue()));
            lastSunday.setClock(fixedClock);
            assertFalse(lastSunday.isCurrentWeek());

        } catch (IllegalValueException e) {
            fail("Exception should not be thrown.");
        }
    }
}
