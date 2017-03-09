package seedu.toluist.commons.util;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

public class DateTimeUtilTest {

    @Test
    public void isSameLocalDateTime() {
        String stringDate;
        LocalDateTime localDateTime1, localDateTime2;

        // convert a fully specified date time to local date time
        stringDate = "31 Mar 2017, 5:24pm";
        localDateTime1 = DateTimeUtil.toDate(stringDate);
        localDateTime2 = LocalDateTime.of(2017, Month.MARCH, 31, 17, 24);
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime2));

        stringDate = "31 Mar 2017, 5am";
        localDateTime1 = DateTimeUtil.toDate(stringDate);
        localDateTime2 = LocalDateTime.of(2017, Month.MARCH, 31, 5, 0);
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime1));

        // convert a fully specified date to local date time
        stringDate = "31 Mar 2017";
        localDateTime1 = DateTimeUtil.toDate(stringDate);
        localDateTime2 = LocalDateTime.of(2017, Month.MARCH, 31,
                LocalDateTime.now().getHour(), LocalDateTime.now().getMinute());
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime2));

        // convert an unclear date time to local date time
        stringDate = "now";
        localDateTime1 = DateTimeUtil.toDate(stringDate);
        localDateTime2 = LocalDateTime.now();
        assertTrue(datesApproximatelyEqual(localDateTime1, localDateTime2));
    }

    public Boolean datesApproximatelyEqual(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        // Dates are approximately equal so long as they are accurate up to the minute.
        return localDateTime1.getYear() == localDateTime2.getYear()
                && localDateTime1.getMonth() == localDateTime2.getMonth()
                && localDateTime1.getDayOfMonth() == localDateTime2.getDayOfMonth()
                && localDateTime1.getHour() == localDateTime2.getHour()
                && localDateTime1.getMinute() == localDateTime2.getMinute();
    }
}
