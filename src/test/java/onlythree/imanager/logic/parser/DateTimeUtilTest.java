package onlythree.imanager.logic.parser;

import static onlythree.imanager.testutil.TestDateTimeHelper.assertEqualsIgnoresUnitBelow;
import static onlythree.imanager.testutil.TestDateTimeHelper.assertNotEqualsIgnoresUnitBelow;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import onlythree.imanager.commons.core.DateTimeFormats;
import onlythree.imanager.commons.exceptions.IllegalValueException;
import onlythree.imanager.logic.DateTimeUtil;

//@@author A0140023E
public class DateTimeUtilTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private ZoneId someZoneId = ZoneId.of("Asia/Tokyo");
    private ZonedDateTime someDateTime = ZonedDateTime.of(2015, 4, 1, 3, 4, 5, 2, someZoneId);

    @Test
    public void isSingleDateTimeString() {
        // Not dates
        assertFalse(DateTimeUtil.isSingleDateTimeString("Hello World"));
        assertFalse(DateTimeUtil.isSingleDateTimeString("Not a date"));
        assertFalse(DateTimeUtil.isSingleDateTimeString("We.d"));

        // multiple date groups separated by unknown tokens
        assertFalse(DateTimeUtil.isSingleDateTimeString("Wed ~ Thursday"));
        assertFalse(DateTimeUtil.isSingleDateTimeString("Wed ` Thursday"));
        assertFalse(DateTimeUtil.isSingleDateTimeString("Wed plus Thursday"));

        // recurring dates
        assertFalse(DateTimeUtil.isSingleDateTimeString("every Friday"));

        // multiple date alternatives
        assertFalse(DateTimeUtil.isSingleDateTimeString("Wed or Thur"));
        assertFalse(DateTimeUtil.isSingleDateTimeString("Wed and Thur"));

        // valid single dates
        assertTrue(DateTimeUtil.isSingleDateTimeString("Sat"));
    }

    @Test
    public void parseDateTimeString_validDateTimeString_dateTimeReturned() throws IllegalValueException {
        // Random days of the week
        assertEqualsIgnoresMilliAndBelow(ZonedDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)),
                DateTimeUtil.parseDateTimeString("Sat"));
        assertEqualsIgnoresMilliAndBelow(ZonedDateTime.now().with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY)),
                DateTimeUtil.parseDateTimeString("Wed"));
        assertEqualsIgnoresMilliAndBelow(ZonedDateTime.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY)),
                DateTimeUtil.parseDateTimeString("Fri"));

        // Random date with month
        assertEqualsIgnoresMilliAndBelow(ZonedDateTime.now().withMonth(4).withDayOfMonth(25),
                DateTimeUtil.parseDateTimeString("25 Apr"));

        // Random month with year, Natty infers that it's 1st day of month
        assertEqualsIgnoresMilliAndBelow(ZonedDateTime.now().withYear(2017).withMonth(1).withDayOfMonth(1),
                DateTimeUtil.parseDateTimeString("Jan 2017"));

        // Random relative date
        assertEqualsIgnoresMilliAndBelow(ZonedDateTime.now().plusDays(2),
                DateTimeUtil.parseDateTimeString("2 days after"));

        // Random explicit date
        assertEqualsIgnoresMilliAndBelow(
                ZonedDateTime.of(LocalDate.of(2016, 5, 2), LocalTime.now(), DateTimeFormats.SYSTEM_TIME_ZONE),
                DateTimeUtil.parseDateTimeString("2016-05-02"));
    }

    @Test
    public void parseDateTimeString_invalidDateTime_throwsException() throws IllegalValueException {
        testInvalidDateTime("Not a date");
    }
    @Test
    public void parseDateTimeString_invalidDateTimeWithSymbols_throwsException() throws IllegalValueException {
        testInvalidDateTime("We.d");
    }

    @Test
    public void parseDateTimeString_multipleDateTimes_throwsException() throws IllegalValueException {
        testMultipleDateTimesFound("Wed ~ Thur");
    }

    @Test
    public void parseDateTimeString_multipleDateTimeAlternatives_throwsException() throws IllegalValueException {
        testMultipleDateTimeAlternativesFound("Wed or Thur");
    }
    @Test
    public void parseDateTimeString_recurringDateTime_throwsException() throws IllegalValueException {
        testRecurringDateTimesFound("every Friday");
    }

    @Test
    public void parseDateTimeString_unrecognizedNaturalLanguage_expectStrangeOutput() throws IllegalValueException {
        assertNotEqualsIgnoresMilliAndBelow(
                ZonedDateTime.now().withMonth(4).withDayOfMonth(25).withHour(20).withMinute(0).withSecond(0)
                        .plusDays(2),
                DateTimeUtil.parseDateTimeString("2 days after 8pm 25 Apr"));

        assertNotEqualsIgnoresMilliAndBelow(
                ZonedDateTime.now().withMonth(4).withDayOfMonth(25).withHour(20).withMinute(0).withSecond(0)
                        .plusHours(2),
                DateTimeUtil.parseDateTimeString("2 hours after 8pm 25 Apr"));

    }

    @Test
    public void parseEditedDateTimeString_relativeDateTime_expectCorrectOutput() throws IllegalValueException {
        // relative date
        assertEqualsIgnoresMilliAndBelow(ZonedDateTime.now().plusDays(2),
                DateTimeUtil.parseEditedDateTimeString("2 days later", someDateTime));
        // relative time
        assertEqualsIgnoresMilliAndBelow(ZonedDateTime.now().plusHours(24),
                DateTimeUtil.parseEditedDateTimeString("24 hours later", someDateTime));

        // relative date respective to another date
        assertEqualsIgnoresMilliAndBelow(ZonedDateTime.now().withMonth(4).withDayOfMonth(25).plusDays(2),
                DateTimeUtil.parseEditedDateTimeString("2 days after 25 Apr", someDateTime));

        // relative date respective to another date-time
        assertEqualsIgnoresMilliAndBelow(
                ZonedDateTime.now().withMonth(4).withDayOfMonth(25).withHour(20).withMinute(0).withSecond(0)
                        .plusDays(2),
                DateTimeUtil.parseEditedDateTimeString("2 days after 25 Apr 8pm", someDateTime));

        // relative date respective to another date-time with time-zone
        // PST and America/Los_Angeles is equivalent but Natty supports only certain time-zone suffixes
        // PST is an example of daylights saving time
        assertEqualsIgnoresMilliAndBelow(
                ZonedDateTime.of(Year.now().getValue(), 4, 25, 20, 0, 0, 0, ZoneId.of("America/Los_Angeles"))
                        .plusDays(5),
                DateTimeUtil.parseEditedDateTimeString("5 days after 25 Apr 8pm PST", someDateTime));

        // relative date respective to another date-time with time-zone offset
        assertEqualsIgnoresMilliAndBelow(
                ZonedDateTime.of(Year.now().getValue(), 4, 25, 20, 0, 0, 0, ZoneId.of("+1000"))
                        .plusDays(3),
                DateTimeUtil.parseEditedDateTimeString("3 days after 25 Apr 8pm +1000", someDateTime));

        // relative date respective to another date with time
        // note that this does not work because Natty is not working correctly
        assertNotEqualsIgnoresMilliAndBelow(
                ZonedDateTime.now().withMonth(4).withDayOfMonth(25).withHour(20).withMinute(0).withSecond(0)
                        .plusHours(2),
                DateTimeUtil.parseEditedDateTimeString("2 hours after 25 Apr 8pm", someDateTime));
        assertNotEqualsIgnoresMilliAndBelow(
                ZonedDateTime.of(Year.now().getValue(), 4, 25, 20, 0, 0, 0, ZoneId.of("America/Los_Angeles"))
                        .plusDays(5),
                DateTimeUtil.parseEditedDateTimeString("5 days after 8pm PST 25 Apr", someDateTime));
    }

    private void testInvalidDateTime(String dateTime) throws IllegalValueException {
        exception.expect(IllegalValueException.class);
        exception.expectMessage(String.format(DateTimeUtil.MESSAGE_NOT_VALID_DATE_TIME, dateTime));
        DateTimeUtil.parseDateTimeString(dateTime);
    }

    private void testMultipleDateTimesFound(String dateTime) throws IllegalValueException {
        exception.expect(IllegalValueException.class);
        exception.expectMessage(String.format(DateTimeUtil.MESSAGE_MULTIPLE_DATE_TIMES_FOUND, dateTime));
        DateTimeUtil.parseDateTimeString(dateTime);
    }

    private void testMultipleDateTimeAlternativesFound(String dateTime) throws IllegalValueException {
        exception.expect(IllegalValueException.class);
        exception.expectMessage(String.format(DateTimeUtil.MESSAGE_MULTIPLE_DATE_TIME_ALTERNATIVES_FOUND, dateTime));
        DateTimeUtil.parseDateTimeString(dateTime);
    }

    private void testRecurringDateTimesFound(String dateTime) throws IllegalValueException {
        exception.expect(IllegalValueException.class);
        exception.expectMessage(String.format(DateTimeUtil.MESSAGE_RECURRING_DATE_TIME_FOUND, dateTime));
        DateTimeUtil.parseDateTimeString(dateTime);
    }

    private void assertEqualsIgnoresMilliAndBelow(ZonedDateTime expected, ZonedDateTime actual) {
        assertEqualsIgnoresUnitBelow(expected, actual, ChronoUnit.SECONDS);
    }
    private void assertNotEqualsIgnoresMilliAndBelow(ZonedDateTime expected, ZonedDateTime actual) {
        assertNotEqualsIgnoresUnitBelow(expected, actual, ChronoUnit.SECONDS);
    }
}
