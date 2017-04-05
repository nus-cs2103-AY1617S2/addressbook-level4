//@@author A0163744B
package seedu.task.model.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;

public class DateParserTest {
    private static final int HOURS_IN_DAY = 24;

    @Test
    public void isValidDateString() {
        // invalid date
        assertFalse(DateParser.isValidDateString(""));
        assertFalse(DateParser.isValidDateString("12/12/1234"));
        assertFalse(DateParser.isValidDateString("12/123412 0000"));
        assertFalse(DateParser.isValidDateString("1234/1212 0000"));
        assertFalse(DateParser.isValidDateString("/01/01200a 0000"));
        assertFalse(DateParser.isValidDateString("/ba/012000 0000"));
        assertFalse(DateParser.isValidDateString("0000"));
        assertFalse(DateParser.isValidDateString("01/23/2000 0000"));
        assertFalse(DateParser.isValidDateString("42/12/2000 0000"));
        assertFalse(DateParser.isValidDateString("01/12/2000 3000"));
        assertFalse(DateParser.isValidDateString("01/12/2000 0160"));
        assertFalse(DateParser.isValidDateString("01/12/2000 20000"));
        assertFalse(DateParser.isValidDateString("010/12/2000 2000"));
        assertFalse(DateParser.isValidDateString("10/123/2000 2000"));
        assertFalse(DateParser.isValidDateString("10/123/20010 2000"));

        assertFalse(DateParser.isValidDateString("a 2000"));
        assertFalse(DateParser.isValidDateString("aaaaaaaaaa 2000"));

        // valid date
        assertTrue(DateParser.isValidDateString("01/01/2000 0000"));
        assertTrue(DateParser.isValidDateString("01/01/2000 1200"));
        assertTrue(DateParser.isValidDateString("01/01/0000 1200"));
        assertTrue(DateParser.isValidDateString("10/03/2017 0317"));
        assertTrue(DateParser.isValidDateString("Mon 0317"));
        assertTrue(DateParser.isValidDateString("Monday 0317"));
        assertTrue(DateParser.isValidDateString("Wednesday 0000"));
        assertTrue(DateParser.isValidDateString("foo 0317"));
    }

    @Test
    public void isValidDate() {
        // invalid date
        assertFalse(DateParser.isValidDate(-1, 0, 1, 0, 0));
        assertFalse(DateParser.isValidDate(2017, -1, 1, 0, 0));
        assertFalse(DateParser.isValidDate(2017, 12, 1, 0, 0));
        assertFalse(DateParser.isValidDate(2017, 0, 0, 0, 0));
        assertFalse(DateParser.isValidDate(2017, 0, 32, 0, 0));
        assertFalse(DateParser.isValidDate(2017, 5, 31, 0, 0));
        assertFalse(DateParser.isValidDate(2017, 0, 1, -1, 0));
        assertFalse(DateParser.isValidDate(2017, 0, 1, 24, 0));
        assertFalse(DateParser.isValidDate(2017, 0, 1, 0, -1));
        assertFalse(DateParser.isValidDate(2017, 0, 1, 0, 60));

        // valid date
        assertTrue(DateParser.isValidDate(0, 0, 1, 0, 0));
        assertTrue(DateParser.isValidDate(2017, 0, 1, 0, 0));
        assertTrue(DateParser.isValidDate(2017, 11, 31, 23, 59));
        assertTrue(DateParser.isValidDate(2017, 0, 31, 0, 0));
        assertTrue(DateParser.isValidDate(2017, 2, 31, 0, 0));
        assertTrue(DateParser.isValidDate(2000, 1, 29, 0, 0));
        assertTrue(DateParser.isValidDate(2008, 8, 8, 8, 0));
        assertTrue(DateParser.isValidDate(2017, 0, 1, 15, 30));
    }

    @Test
    public void parse() throws IllegalValueException {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(2000, 00, 01, 0, 0, 0);
        assertEquals(
                cal.getTimeInMillis(),
                DateParser.parse("01/01/2000 0000").getTimeInMillis());
    }

    /**
     * Asserts that the actual date is the same as the expected. Checks the year, month, and day
     * @param expected the expected date
     * @param actual the actual date to compare with the expected
     */
    private void assertDatesEqual(Calendar expected, Calendar actual) {
        assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
        assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Finds the number of hours from the current time until the next instance of the day
     * given in {@code dayOfWeek}. (e.g. if it is Saturday, it will return 48h until Monday)
     * @param dayOfWeek the day of the week from {@code Calendar} (e.g. {@code Calendar.MONDAY})
     * @return the number of hours from now until the given day. Will be a multiple of 24.
     */
    private int numberOfHoursUntilNextDayOfWeek(int dayOfWeek) {
        int hoursToAdd = 0;
        Calendar cal = Calendar.getInstance();
        while (cal.get(Calendar.DAY_OF_WEEK) != dayOfWeek) {
            hoursToAdd += HOURS_IN_DAY;
            cal.add(Calendar.HOUR, HOURS_IN_DAY);
        }
        return hoursToAdd;
    }

    @Test
    public void parseRelativeDates() throws IllegalValueException {
        Calendar today = Calendar.getInstance();
        assertDatesEqual(today, DateParser.parse("today 1200"));

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.HOUR, 24);
        assertDatesEqual(tomorrow, DateParser.parse("tomorrow 1200"));

        Calendar monday = Calendar.getInstance();
        monday.add(Calendar.HOUR, numberOfHoursUntilNextDayOfWeek(Calendar.MONDAY));
        assertDatesEqual(monday, DateParser.parse("monday 1200"));
        assertDatesEqual(monday, DateParser.parse("mon 1200"));
        assertDatesEqual(monday, DateParser.parse("Monday 1200"));
        assertDatesEqual(monday, DateParser.parse("mOnDay 1200"));

        Calendar tuesday = Calendar.getInstance();
        tuesday.add(Calendar.HOUR, numberOfHoursUntilNextDayOfWeek(Calendar.TUESDAY));
        assertDatesEqual(tuesday, DateParser.parse("tuesday 1200"));

        Calendar wednesday = Calendar.getInstance();
        wednesday.add(Calendar.HOUR, numberOfHoursUntilNextDayOfWeek(Calendar.WEDNESDAY));
        assertDatesEqual(wednesday, DateParser.parse("wednesday 1200"));

        Calendar thursday = Calendar.getInstance();
        thursday.add(Calendar.HOUR, numberOfHoursUntilNextDayOfWeek(Calendar.THURSDAY));
        assertDatesEqual(thursday, DateParser.parse("thursday 1200"));

        Calendar friday = Calendar.getInstance();
        friday.add(Calendar.HOUR, numberOfHoursUntilNextDayOfWeek(Calendar.FRIDAY));
        assertDatesEqual(friday, DateParser.parse("friday 1200"));

        Calendar saturday = Calendar.getInstance();
        saturday.add(Calendar.HOUR, numberOfHoursUntilNextDayOfWeek(Calendar.SATURDAY));
        assertDatesEqual(saturday, DateParser.parse("saturday 1200"));

        Calendar sunday = Calendar.getInstance();
        sunday.add(Calendar.HOUR, numberOfHoursUntilNextDayOfWeek(Calendar.SUNDAY));
        assertDatesEqual(sunday, DateParser.parse("sunday 1200"));
    }

    @Test
    public void toStringTest() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(2000, 00, 01, 0, 0, 0);
        assertEquals("01/01/2000 0000", DateParser.toString(cal));
        cal.set(1990, 05, 22, 23, 54, 0);
        assertEquals("22/06/1990 2354", DateParser.toString(cal));
    }
}
