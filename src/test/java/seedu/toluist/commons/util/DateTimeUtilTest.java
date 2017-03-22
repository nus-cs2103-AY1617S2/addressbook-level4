//@@author A0131125Y
package seedu.toluist.commons.util;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

/**
 * Tests for DateTimeUtil
 */
public class DateTimeUtilTest {

    //@@author Melvin
    @Test
    public void isSameLocalDateTime() {
        String stringDate;
        LocalDateTime localDateTime1;
        LocalDateTime localDateTime2;

        // convert a fully specified date time to local date time
        stringDate = "31 Mar 2017, 5:24pm";
        localDateTime1 = DateTimeUtil.parseDateString(stringDate);
        localDateTime2 = LocalDateTime.of(2017, Month.MARCH, 31, 17, 24);
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime2));

        stringDate = "31 Mar 2017, 5am";
        localDateTime1 = DateTimeUtil.parseDateString(stringDate);
        localDateTime2 = LocalDateTime.of(2017, Month.MARCH, 31, 5, 0);
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime1));

        // convert a fully specified date to local date time
        stringDate = "31 Mar 2017";
        localDateTime1 = DateTimeUtil.parseDateString(stringDate);
        localDateTime2 = LocalDateTime.of(2017, Month.MARCH, 31,
                LocalDateTime.now().getHour(), LocalDateTime.now().getMinute());
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime2));

        // convert an unclear date time to local date time
        stringDate = "now";
        localDateTime1 = DateTimeUtil.parseDateString(stringDate);
        localDateTime2 = LocalDateTime.now();
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime2));

        stringDate = "3 hours from now";
        localDateTime1 = DateTimeUtil.parseDateString(stringDate);
        localDateTime2 = LocalDateTime.now();
        localDateTime2 = localDateTime2.plus(3, ChronoUnit.HOURS);
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime2));

        stringDate = "tomorrow";
        localDateTime1 = DateTimeUtil.parseDateString(stringDate);
        localDateTime2 = LocalDateTime.now();
        localDateTime2 = localDateTime2.plus(1, ChronoUnit.DAYS);
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime2));

        stringDate = "next week";
        localDateTime1 = DateTimeUtil.parseDateString(stringDate);
        localDateTime2 = LocalDateTime.now();
        localDateTime2 = localDateTime2.plus(1, ChronoUnit.WEEKS);
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime2));

        stringDate = "next month";
        localDateTime1 = DateTimeUtil.parseDateString(stringDate);
        localDateTime2 = LocalDateTime.now();
        localDateTime2 = localDateTime2.plus(1, ChronoUnit.MONTHS);
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime2));

        stringDate = "next year";
        localDateTime1 = DateTimeUtil.parseDateString(stringDate);
        localDateTime2 = LocalDateTime.now();
        localDateTime2 = localDateTime2.plus(1, ChronoUnit.YEARS);
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime2));
    }

    @Test
    public void dateTimeIsNull() {
        String stringDate;
        LocalDateTime localDateTime;

        localDateTime = DateTimeUtil.parseDateString(null);
        assertTrue(localDateTime == null);

        stringDate = "buy banana";
        localDateTime = DateTimeUtil.parseDateString(stringDate);
        assertTrue(localDateTime == null);
    }

    //@@author A0131125Y
    @Test
    public void isToday_startOfToday_isTrue() {
        ZonedDateTime datetime = ZonedDateTime.now().toLocalDate().atStartOfDay(ZoneId.systemDefault());
        LocalDateTime startOfToday =  LocalDateTime.ofInstant(datetime.toInstant(), ZoneId.systemDefault());
        assertTrue(DateTimeUtil.isToday(startOfToday));
    }

    @Test
    public void isToday_endOfToday_isTrue() {
        ZonedDateTime datetime = ZonedDateTime.now().toLocalDate().plusDays(1)
                .atStartOfDay(ZoneId.systemDefault()).minusSeconds(1);
        LocalDateTime endOfToday =  LocalDateTime.ofInstant(datetime.toInstant(), ZoneId.systemDefault());
        assertTrue(DateTimeUtil.isToday(endOfToday));
    }

    @Test
    public void isToday_startOfTomorrow_isFalse() {
        ZonedDateTime datetime = ZonedDateTime.now().toLocalDate().plusDays(1)
                .atStartOfDay(ZoneId.systemDefault());
        LocalDateTime startOfTomorrow =  LocalDateTime.ofInstant(datetime.toInstant(), ZoneId.systemDefault());
        assertFalse(DateTimeUtil.isToday(startOfTomorrow));
    }

    @Test
    public void isToday_endOfYesterday_isFalse() {
        ZonedDateTime datetime = ZonedDateTime.now().toLocalDate().atStartOfDay(ZoneId.systemDefault()).minusSeconds(1);
        LocalDateTime endOfYesterday =  LocalDateTime.ofInstant(datetime.toInstant(), ZoneId.systemDefault());
        assertFalse(DateTimeUtil.isToday(endOfYesterday));
    }

    @Test
    public void isYesterday_startOfYesterday_isTrue() {
        ZonedDateTime datetime = ZonedDateTime.now().toLocalDate().atStartOfDay(ZoneId.systemDefault()).minusDays(1);
        LocalDateTime startOfYesterday =  LocalDateTime.ofInstant(datetime.toInstant(), ZoneId.systemDefault());
        assertTrue(DateTimeUtil.isYesterday(startOfYesterday));
    }

    @Test
    public void isYesterday_endOfYesterday_isTrue() {
        ZonedDateTime datetime = ZonedDateTime.now().toLocalDate().atStartOfDay(ZoneId.systemDefault()).minusSeconds(1);
        LocalDateTime endOfYesterday =  LocalDateTime.ofInstant(datetime.toInstant(), ZoneId.systemDefault());
        assertTrue(DateTimeUtil.isYesterday(endOfYesterday));
    }

    @Test
    public void isYesterday_startOfToday_isFalse() {
        ZonedDateTime datetime = ZonedDateTime.now().toLocalDate().atStartOfDay(ZoneId.systemDefault());
        LocalDateTime startOfToday =  LocalDateTime.ofInstant(datetime.toInstant(), ZoneId.systemDefault());
        assertFalse(DateTimeUtil.isYesterday(startOfToday));
    }

    @Test
    public void isYesterday_endOfTheDayBeforeYesterday_isFalse() {
        ZonedDateTime datetime = ZonedDateTime.now().toLocalDate().minusDays(1)
                .atStartOfDay(ZoneId.systemDefault()).minusSeconds(1);
        LocalDateTime endOfTheDayBeforeYesterday =  LocalDateTime.ofInstant(datetime.toInstant(),
                ZoneId.systemDefault());
        assertFalse(DateTimeUtil.isYesterday(endOfTheDayBeforeYesterday));
    }

    @Test
    public void isTomorrow_startOfTomorrow_isTrue() {
        ZonedDateTime datetime = ZonedDateTime.now().toLocalDate().atStartOfDay(ZoneId.systemDefault()).plusDays(1);
        LocalDateTime startOfTomorrow =  LocalDateTime.ofInstant(datetime.toInstant(), ZoneId.systemDefault());
        assertTrue(DateTimeUtil.isTomorrow(startOfTomorrow));
    }

    @Test
    public void isTomorrow_endOfTomorrow_isTrue() {
        ZonedDateTime datetime = ZonedDateTime.now().toLocalDate().plusDays(2)
                .atStartOfDay(ZoneId.systemDefault()).minusSeconds(1);
        LocalDateTime endOfTomorrow =  LocalDateTime.ofInstant(datetime.toInstant(), ZoneId.systemDefault());
        assertTrue(DateTimeUtil.isTomorrow(endOfTomorrow));
    }

    @Test
    public void isTomorrow_startOfTheDayAfterTomorrow_isFalse() {
        ZonedDateTime datetime = ZonedDateTime.now().toLocalDate().atStartOfDay(ZoneId.systemDefault()).plusDays(2);
        LocalDateTime startOfTheDayAfterTomorrow =  LocalDateTime.ofInstant(datetime.toInstant(),
                ZoneId.systemDefault());
        assertFalse(DateTimeUtil.isTomorrow(startOfTheDayAfterTomorrow));
    }

    @Test
    public void isTomorrow_endOfToday_isFalse() {
        ZonedDateTime datetime = ZonedDateTime.now().toLocalDate().plusDays(1)
                .atStartOfDay(ZoneId.systemDefault()).minusSeconds(1);
        LocalDateTime endOfToday =  LocalDateTime.ofInstant(datetime.toInstant(),
                ZoneId.systemDefault());
        assertFalse(DateTimeUtil.isTomorrow(endOfToday));
    }

    @Test
    public void isBeforeOrEqual_twoNulls_isTrue() {
        assertTrue(DateTimeUtil.isBeforeOrEqual(null, null));
    }

    @Test
    public void isBeforeOrEqual_nullAndNonNull_isFalse() {
        assertFalse(DateTimeUtil.isBeforeOrEqual(null, LocalDateTime.now()));
    }

    @Test
    public void isBeforeOrEqual_nonNullAndNull_isTrue() {
        assertTrue(DateTimeUtil.isBeforeOrEqual(LocalDateTime.now(), null));
    }

    @Test
    public void isBeforeOrEqual_leftAfterRight_isFalse() {
        assertFalse(DateTimeUtil.isBeforeOrEqual(LocalDateTime.MAX, LocalDateTime.MIN));
    }

    @Test
    public void isBeforeOrEqual_leftEqualsRight_isTrue() {
        assertTrue(DateTimeUtil.isBeforeOrEqual(LocalDateTime.MIN, LocalDateTime.MIN));
    }

    @Test
    public void isBeforeOrEqual_leftBeforeRight_isTrue() {
        assertTrue(DateTimeUtil.isBeforeOrEqual(LocalDateTime.MIN, LocalDateTime.MAX));
    }

    //@@author Melvin
    private boolean datesApproximatelyEqual(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        // Dates are approximately equal so long as they are accurate up to the minute.
        return localDateTime1.getYear() == localDateTime2.getYear()
                && localDateTime1.getMonth() == localDateTime2.getMonth()
                && localDateTime1.getDayOfMonth() == localDateTime2.getDayOfMonth()
                && localDateTime1.getHour() == localDateTime2.getHour()
                && localDateTime1.getMinute() == localDateTime2.getMinute();
    }
}
