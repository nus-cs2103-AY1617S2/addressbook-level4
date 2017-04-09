package onlythree.imanager.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import onlythree.imanager.model.task.Task;

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

    public static void assertEqualsIgnoresSecondsAndBelow(ZonedDateTime expected, ZonedDateTime actual) {
        assertEqualsIgnoresUnitBelow(expected, actual, ChronoUnit.MINUTES);
    }

    public static void assertEqualsTaskIgnoreDeadlineSeconds(Task expectedTask, Task actualTask) {
        assertEquals(expectedTask.getName(), actualTask.getName());
        assertEqualsIgnoresSecondsAndBelow(expectedTask.getDeadline().get().getDateTime(),
                actualTask.getDeadline().get().getDateTime());
        assertEquals(expectedTask.getStartEndDateTime(), actualTask.getStartEndDateTime());
        assertEquals(expectedTask.getTags(), actualTask.getTags());
        assertEquals(expectedTask.isComplete(), actualTask.isComplete());
    }

    public static void assertEqualsTaskIgnoreStartEndDateTimeSeconds(Task expectedTask, Task actualTask) {
        assertEquals(expectedTask.getName(), actualTask.getName());
        assertEquals(expectedTask.getDeadline(), actualTask.getDeadline());
        assertEqualsIgnoresSecondsAndBelow(expectedTask.getStartEndDateTime().get().getStartDateTime(),
                actualTask.getStartEndDateTime().get().getStartDateTime());
        assertEqualsIgnoresSecondsAndBelow(expectedTask.getStartEndDateTime().get().getEndDateTime(),
                actualTask.getStartEndDateTime().get().getEndDateTime());
        assertEquals(expectedTask.getTags(), actualTask.getTags());
        assertEquals(expectedTask.isComplete(), actualTask.isComplete());
    }

    /**
     * Asserts that two zoned date-times are equal ignoring the unit below the truncationUnit.
     */
    public static void assertEqualsIgnoresUnitBelow(ZonedDateTime expected, ZonedDateTime actual,
            ChronoUnit truncationUnit) {
        ZonedDateTime expectedTruncated = expected.truncatedTo(truncationUnit);
        ZonedDateTime actualTruncated = actual.truncatedTo(truncationUnit);
        assertIsEqual(expectedTruncated, actualTruncated);
    }

    /**
     * Asserts that two zoned date-times are not equal ignoring the unit below the truncationUnit.
     */
    public static void assertNotEqualsIgnoresUnitBelow(ZonedDateTime expected, ZonedDateTime actual,
            ChronoUnit truncationUnit) {
        ZonedDateTime expectedTruncated = expected.truncatedTo(truncationUnit);
        ZonedDateTime actualTruncated = actual.truncatedTo(truncationUnit);
        assertIsNotEqual(expectedTruncated, actualTruncated);
    }

    /**
     * Assert that the instant of the ZonedDateTimes are equal instead of comparing component by component.
     * @see ChronoZonedDateTime#isEqual(ChronoZonedDateTime)
     */
    public static void assertIsEqual(ZonedDateTime expected, ZonedDateTime actual) {
        assertEquals(expected.toInstant(), actual.toInstant());
    }

    /**
     * Assert that the instant of the ZonedDateTimes are not equal instead of comparing component by component.
     * @see ChronoZonedDateTime#isEqual(ChronoZonedDateTime)
     */
    public static  void assertIsNotEqual(ZonedDateTime expected, ZonedDateTime actual) {
        assertNotEquals(expected.toInstant(), actual.toInstant());
    }

}
