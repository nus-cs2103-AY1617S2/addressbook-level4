package onlythree.imanager.testutil;

import java.time.ZonedDateTime;

//@@author A0140023E
/**
 * A utility class for testing and manipulating date-times
 *
 */
public class TestDateTimeHelper {
    public static ZonedDateTime getOneNanoBeforeCurrentDateTime() {
        return ZonedDateTime.now().minusNanos(1);
    }

    public static ZonedDateTime getMinutesAfterCurrentDateTime(int minutes) {
        return ZonedDateTime.now().plusMinutes(minutes);
    }

    public static ZonedDateTime getDaysBeforeCurrentDateTime(int days) {
        return ZonedDateTime.now().minusDays(days);
    }

    public static ZonedDateTime getDaysAfterCurrentDateTime(int days) {
        return ZonedDateTime.now().plusDays(days);
    }
}
