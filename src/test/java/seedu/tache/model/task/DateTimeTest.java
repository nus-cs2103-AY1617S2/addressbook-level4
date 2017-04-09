//@@author A0139925U
package seedu.tache.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import seedu.tache.commons.exceptions.IllegalValueException;

public class DateTimeTest {

    @Test(expected = IllegalValueException.class)
    public void dateTime_invalidDateTime_failure() throws IllegalValueException {
        new DateTime("happy");
        new DateTime("");
    }

    @Test
    public void dateTime_correctDateParsing_success() throws IllegalValueException {
        DateTime test = new DateTime("02/07/19");
        assertEquals(test.getAmericanDateOnly(), "02/07/2019");
        assertEquals(test.getDateOnly(), "07/02/2019");
        assertEquals(test.getAmericanDateTime(), "02/07/2019 00:00:00");
        assertEquals(test.getTimeOnly(), "00:00:00");
        assertEquals(test.getDateTimeForFullCalendar(), "2019-02-07T00:00:00");
    }

    @Test
    public void dateTime_correctTimeParsing_success() throws IllegalValueException {
        DateTime test = new DateTime("3pm");
        assertEquals(test.getTimeOnly(), "15:00:00");
    }

    @Test
    public void dateTime_correctDateTimeParsing_success() throws IllegalValueException {
        DateTime test = new DateTime("02/07/19 3pm");
        assertEquals(test.getAmericanDateOnly(), "02/07/2019");
        assertEquals(test.getDateOnly(), "07/02/2019");
        assertEquals(test.getAmericanDateTime(), "02/07/2019 15:00:00");
        assertEquals(test.getTimeOnly(), "15:00:00");
        assertEquals(test.getDateTimeForFullCalendar(), "2019-02-07T15:00:00");

        DateTime test2 = new DateTime("3pm 02/07/19");
        assertEquals(test2.getAmericanDateOnly(), "02/07/2019");
        assertEquals(test2.getDateOnly(), "07/02/2019");
        assertEquals(test2.getAmericanDateTime(), "02/07/2019 15:00:00");
        assertEquals(test2.getTimeOnly(), "15:00:00");
        assertEquals(test2.getDateTimeForFullCalendar(), "2019-02-07T15:00:00");

        DateTime test3 = new DateTime("3pm 02-07-19");
        assertEquals(test3.getAmericanDateOnly(), "02/07/2019");
        assertEquals(test3.getDateOnly(), "07/02/2019");
        assertEquals(test3.getAmericanDateTime(), "02/07/2019 15:00:00");
        assertEquals(test3.getTimeOnly(), "15:00:00");
        assertEquals(test3.getDateTimeForFullCalendar(), "2019-02-07T15:00:00");
    }

    //@@author A0139961U
    @Test
    public void dateTime_isSameDate_success() throws IllegalValueException {
        Date now = new Date();
        DateTime test = new DateTime(now.toString());
        Date date = new Date();
        assertTrue(test.isSameDate(date));
    }

    @Test
    public void dateTime_isSameDate_failure() throws IllegalValueException {
        Date now = new Date();
        DateTime test = new DateTime(now.toString());
        Date date = new Date(0);
        assertFalse(test.isSameDate(date));
    }

    @Test
    public void dateTime_isToday_success() throws IllegalValueException {
        Date today = new Date();
        DateTime test = new DateTime(today.toString());
        assertTrue(test.isToday());
    }

    @Test
    public void dateTime_isToday_failure() throws IllegalValueException {
        Date past = new Date(0);
        DateTime testPast = new DateTime(past.toString());
        assertFalse(testPast.isToday());

        DateTime yesterday = new DateTime("yesterday");
        assertFalse(yesterday.isToday());

        DateTime tomorrow = new DateTime("tomorrow");
        assertFalse(tomorrow.isToday());

        Date future = new Date(Long.MAX_VALUE);
        DateTime testFuture = new DateTime(future.toString());
        assertFalse(testFuture.isToday());
    }

    @Test
    public void dateTime_isThisWeek_success() throws IllegalValueException {
        DateTime thisWeek = new DateTime("this week");
        assertTrue(thisWeek.isThisWeek());
    }

    @Test
    public void dateTime_isThisWeek_failure() throws IllegalValueException {
        DateTime lastWeek = new DateTime("last week");
        assertFalse(lastWeek.isThisWeek());

        DateTime nextWeek = new DateTime("next week");
        assertFalse(nextWeek.isThisWeek());
    }

    @Test
    public void dateTime_removeTime_success() throws IllegalValueException {
        Date date = new Date();
        DateTime today = new DateTime("today 00:00AM");
        assertEquals(DateTime.removeTime(date), today.getDate());
    }
    
    //@@author A0142255M
    @Test
    public void dateTime_compareTo() throws IllegalValueException {
        DateTime yesterday = new DateTime("yesterday 3pm");
        DateTime alsoYesterday = new DateTime("yesterday 3pm");
        DateTime today = new DateTime("today");
        
        assertEquals(0, yesterday.compareTo(alsoYesterday));
        assertEquals(1, today.compareTo(yesterday));
        assertEquals(-1, yesterday.compareTo(today));
    }
}
