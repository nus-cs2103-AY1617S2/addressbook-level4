//@@author A0131125Y
package seedu.toluist.commons.util;

import static junit.framework.TestCase.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;

/**
 * Tests for DateTimeFormatter
 */
public class DateTimeFormatterUtilTest {
    @Test
    public void formatDate() {
        // Test format normal date
        LocalDateTime aDateTime = LocalDateTime.of(2009, 12, 1, 12, 22);
        String expectedADateTime = "Tue, 01 Dec 2009";
        assertEquals(expectedADateTime, DateTimeFormatterUtil.formatDate(aDateTime));

        // Test format today
        LocalDateTime today = LocalDateTime.now();
        String expectedToday = DateTimeFormatterUtil.TODAY;
        assertEquals(expectedToday, DateTimeFormatterUtil.formatDate(today));

        // Test format yesterday
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        String expectedYesterday = DateTimeFormatterUtil.YESTERDAY;
        assertEquals(expectedYesterday, DateTimeFormatterUtil.formatDate(yesterday));

        // Test format today
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        String expectedTomorrow = DateTimeFormatterUtil.TOMORROW;
        assertEquals(expectedTomorrow, DateTimeFormatterUtil.formatDate(tomorrow));
    }

    @Test
    public void formatTime() {
        // Test format time AM
        LocalDateTime dateTimeAM = LocalDateTime.of(1 , 1 , 1 , 1, 2);
        String expectedAM = "01:02 AM";
        assertEquals(expectedAM, DateTimeFormatterUtil.formatTime(dateTimeAM));

        // Test format time PM
        LocalDateTime dateTimePM = LocalDateTime.of(1 , 1 , 1 , 23, 2);
        String expectedPM = "11:02 PM";
        assertEquals(expectedPM, DateTimeFormatterUtil.formatTime(dateTimePM));
    }

    @Test
    public void formatTaskDeadline() {
        LocalDateTime deadline = LocalDateTime.of(2008, 12, 1, 12, 22);
        String expectedDeadline = "by Mon, 01 Dec 2008, 12:22 PM";
        assertEquals(expectedDeadline, DateTimeFormatterUtil.formatTaskDeadline(deadline));
    }

    @Test
    public void formatEventRange() {
        // Different days for from and to
        LocalDateTime fromDifferentDay = LocalDateTime.of(2008, 12, 1, 12, 22);
        LocalDateTime toDifferentDay = LocalDateTime.of(2009, 12, 1, 13, 22);
        String expectedDifferentDayFromTo = "Mon, 01 Dec 2008, 12:22 PM to Tue, 01 Dec 2009, 01:22 PM";
        assertEquals(expectedDifferentDayFromTo,
                DateTimeFormatterUtil.formatEventRange(fromDifferentDay, toDifferentDay));

        // Same day for from and to
        LocalDateTime fromSameDay = LocalDateTime.of(2009, 12, 1, 13, 22);
        LocalDateTime toSameDay = LocalDateTime.of(2009, 12, 1, 17, 22);
        String expectedSameDayFromTo = "Tue, 01 Dec 2009, 01:22 PM to 05:22 PM";
        assertEquals(expectedSameDayFromTo,
                DateTimeFormatterUtil.formatEventRange(fromSameDay, toSameDay));
    }
}
