package seedu.toluist.commons.util;

import static junit.framework.TestCase.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;

import seedu.toluist.model.Task.RecurringFrequency;

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

    @Test
    public void formatRecurringTaskDeadline() {
        LocalDateTime deadline = LocalDateTime.of(2008, 12, 1, 12, 22);
        LocalDateTime recurringUntilEndDate = LocalDateTime.of(2009, 12, 1, 17, 22);

        RecurringFrequency daily = RecurringFrequency.DAILY;
        String expectedResult = "Every 12:22 PM of the day";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringTaskDeadline(deadline, null, daily));
        expectedResult = "Every 12:22 PM of the day until Tue, 01 Dec 2009";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringTaskDeadline(deadline, recurringUntilEndDate, daily));

        RecurringFrequency weekly = RecurringFrequency.WEEKLY;
        expectedResult = "Every Monday of the week";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringTaskDeadline(deadline, null, weekly));
        expectedResult = "Every Monday of the week until Tue, 01 Dec 2009";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringTaskDeadline(deadline, recurringUntilEndDate, weekly));

        RecurringFrequency monthly = RecurringFrequency.MONTHLY;
        expectedResult = "Every 1st of the month";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringTaskDeadline(deadline, null, monthly));
        expectedResult = "Every 1st of the month until Tue, 01 Dec 2009";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringTaskDeadline(deadline, recurringUntilEndDate, monthly));

        RecurringFrequency yearly = RecurringFrequency.YEARLY;
        expectedResult = "Every 1st December of the year";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringTaskDeadline(deadline, null, yearly));
        expectedResult = "Every 1st December of the year until Tue, 01 Dec 2009";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringTaskDeadline(deadline, recurringUntilEndDate, yearly));
    }

    @Test
    public void formatRecurringEvent() {
        LocalDateTime from;
        LocalDateTime to;
        LocalDateTime recurringUntilEndDate = LocalDateTime.of(2009, 12, 1, 17, 22);

        // same time
        from = LocalDateTime.of(2008, 12, 1, 12, 22);
        to = LocalDateTime.of(2008, 12, 2, 12, 22);
        RecurringFrequency daily = RecurringFrequency.DAILY;
        String expectedResult = "Every 12:22 PM of the day";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringEvent(from, to, null, daily));
        expectedResult = "Every 12:22 PM of the day until Tue, 01 Dec 2009";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringEvent(from, to, recurringUntilEndDate, daily));

        // same day of week
        from = LocalDateTime.of(2008, 12, 1, 12, 22);
        to = LocalDateTime.of(2008, 12, 1, 18, 22);
        RecurringFrequency weekly = RecurringFrequency.WEEKLY;
        expectedResult = "Every Monday of the week";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringEvent(from, to, null, weekly));
        expectedResult = "Every Monday of the week until Tue, 01 Dec 2009";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringEvent(from, to, recurringUntilEndDate, weekly));

        // same day of month
        from = LocalDateTime.of(2008, 12, 1, 12, 22);
        to = LocalDateTime.of(2008, 12, 1, 18, 22);
        RecurringFrequency monthly = RecurringFrequency.MONTHLY;
        expectedResult = "Every 1st of the month";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringEvent(from, to, null, monthly));
        expectedResult = "Every 1st of the month until Tue, 01 Dec 2009";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringEvent(from, to, recurringUntilEndDate, monthly));

        // same date of year
        from = LocalDateTime.of(2008, 12, 1, 12, 22);
        to = LocalDateTime.of(2008, 12, 1, 18, 22);
        RecurringFrequency yearly = RecurringFrequency.YEARLY;
        expectedResult = "Every 1st December of the year";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringEvent(from, to, null, yearly));
        expectedResult = "Every 1st December of the year until Tue, 01 Dec 2009";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringEvent(from, to, recurringUntilEndDate, yearly));

        // same time
        from = LocalDateTime.of(2008, 12, 1, 12, 22);
        to = LocalDateTime.of(2008, 12, 1, 17, 22);
        expectedResult = "Every 12:22 PM to 05:22 PM of the day";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringEvent(from, to, null, daily));
        expectedResult = "Every 12:22 PM to 05:22 PM of the day until Tue, 01 Dec 2009";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringEvent(from, to, recurringUntilEndDate, daily));

        // different day of week
        from = LocalDateTime.of(2008, 12, 1, 12, 22);
        to = LocalDateTime.of(2008, 12, 5, 18, 22);
        expectedResult = "Every Monday to Friday of the week";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringEvent(from, to, null, weekly));
        expectedResult = "Every Monday to Friday of the week until Tue, 01 Dec 2009";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringEvent(from, to, recurringUntilEndDate, weekly));

        // different day of month
        from = LocalDateTime.of(2008, 12, 1, 12, 22);
        to = LocalDateTime.of(2009, 12, 30, 18, 22);
        expectedResult = "Every 1st to 30th of the month";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringEvent(from, to, null, monthly));
        expectedResult = "Every 1st to 30th of the month until Tue, 01 Dec 2009";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringEvent(from, to, recurringUntilEndDate, monthly));

        // different date of year
        from = LocalDateTime.of(2008, 12, 1, 12, 22);
        to = LocalDateTime.of(2009, 3, 1, 18, 22);
        expectedResult = "Every 1st December to 1st March of the year";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringEvent(from, to, null, yearly));
        expectedResult = "Every 1st December to 1st March of the year until Tue, 01 Dec 2009";
        assertEquals(expectedResult,
                DateTimeFormatterUtil.formatRecurringEvent(from, to, recurringUntilEndDate, yearly));

    }

    @Test
    public void getDayNumberSuffix() {
        int[] days = {1, 2, 3, 4, 10, 11, 12, 13, 14, 20, 21, 22, 23, 24, 30, 31};
        String[] suffixes = {"st", "nd", "rd", "th", "th",
                             "th", "th", "th", "th", "th",
                             "st", "nd", "rd", "th", "th",
                             "st"};
        for (int i = 0; i < 16; i++) {
            assertEquals(suffixes[i], DateTimeFormatterUtil.getDayNumberSuffix(days[i]));
        }
    }
}
