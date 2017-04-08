package onlythree.imanager.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
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

import onlythree.imanager.commons.exceptions.IllegalValueException;

//@@author A0140023E
// TODO improve test and naming
public class DateTimeUtilTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void parseDateTimeString_validDateTimes_noExceptionThrown() throws IllegalValueException {
        //org.apache.log4j.Logger.getRootLogger().setLevel(Level.INFO);

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
                ZonedDateTime.of(LocalDate.of(2016, 5, 2), LocalTime.now(), DateTimeUtil.TIME_ZONE),
                DateTimeUtil.parseDateTimeString("2016-05-02"));
    }

    @Test
    public void parseDateTimeString_invalidDateTime_throwsException() throws IllegalValueException {
        assertInvalidDateTime("Not a date");
    }
    @Test
    public void parseDateTimeString_invalidDateTimeWithSymbols_throwsException() throws IllegalValueException {
        assertInvalidDateTime("We.d");
    }

    @Test
    public void parseDateTimeString_multipleDateTimes_throwsException() throws IllegalValueException {
        assertMultipleDateTimesFound("Wed ~ Thur");
    }

    @Test
    public void parseDateTimeString_multipleDateTimeAlternatives_throwsException() throws IllegalValueException {
        assertMultipleDateTimeAlternativesFound("Wed or Thur");
    }
    @Test
    public void parseDateTimeString_recurringDateTime_throwsException() throws IllegalValueException {
        assertRecurringDateTimesFound("every Friday");
    }

    public void assertInvalidDateTime(String dateTime) throws IllegalValueException {
        exception.expect(IllegalValueException.class);
        exception.expectMessage(String.format(DateTimeUtil.MESSAGE_NOT_VALID_DATE_TIME, dateTime));
        DateTimeUtil.parseDateTimeString(dateTime);
    }

    public void assertMultipleDateTimesFound(String dateTime) throws IllegalValueException {
        exception.expect(IllegalValueException.class);
        exception.expectMessage(String.format(DateTimeUtil.MESSAGE_MULTIPLE_DATE_TIMES_FOUND, dateTime));
        DateTimeUtil.parseDateTimeString(dateTime);
    }

    public void assertMultipleDateTimeAlternativesFound(String dateTime) throws IllegalValueException {
        exception.expect(IllegalValueException.class);
        exception.expectMessage(String.format(DateTimeUtil.MESSAGE_MULTIPLE_DATE_TIME_ALTERNATIVES_FOUND, dateTime));
        DateTimeUtil.parseDateTimeString(dateTime);
    }

    public void assertRecurringDateTimesFound(String dateTime) throws IllegalValueException {
        exception.expect(IllegalValueException.class);
        exception.expectMessage(String.format(DateTimeUtil.MESSAGE_RECURRING_DATE_TIME_FOUND, dateTime));
        DateTimeUtil.parseDateTimeString(dateTime);
    }

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

    // TODO all these also
    @Test
    public void testingOnly() throws IllegalValueException {
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
    public void parseEditedDateTimeString() throws IllegalValueException {
        ZoneId fixedRandomZoneId = ZoneId.of("Asia/Tokyo");
        ZonedDateTime fixedRandomDateTime = ZonedDateTime.of(2015, 4, 1, 3, 4, 5, 2, fixedRandomZoneId);

        // relative date
        assertEqualsIgnoresMilliAndBelow(ZonedDateTime.now().plusDays(2),
                DateTimeUtil.parseEditedDateTimeString("2 days later", fixedRandomDateTime));
        // relative time
        assertEqualsIgnoresMilliAndBelow(ZonedDateTime.now().plusHours(24),
                DateTimeUtil.parseEditedDateTimeString("24 hours later", fixedRandomDateTime));

        // relative date respective to another date
        assertEqualsIgnoresMilliAndBelow(ZonedDateTime.now().withMonth(4).withDayOfMonth(25).plusDays(2),
                DateTimeUtil.parseEditedDateTimeString("2 days after 25 Apr", fixedRandomDateTime));

        // relative date respective to another date-time
        assertEqualsIgnoresMilliAndBelow(
                ZonedDateTime.now().withMonth(4).withDayOfMonth(25).withHour(20).withMinute(0).withSecond(0)
                        .plusDays(2),
                DateTimeUtil.parseEditedDateTimeString("2 days after 25 Apr 8pm", fixedRandomDateTime));

        // relative date respective to another date-time with time-zone
        // PST and America/Los_Angeles is equivalent but Natty supports only certain time-zone suffixes
        // PST is an example of daylights saving time
        assertEqualsIgnoresMilliAndBelow(
                ZonedDateTime.of(Year.now().getValue(), 4, 25, 20, 0, 0, 0, ZoneId.of("America/Los_Angeles"))
                        .plusDays(5),
                DateTimeUtil.parseEditedDateTimeString("5 days after 25 Apr 8pm PST", fixedRandomDateTime));

        // relative date respective to another date-time with time-zone offset
        assertEqualsIgnoresMilliAndBelow(
                ZonedDateTime.of(Year.now().getValue(), 4, 25, 20, 0, 0, 0, ZoneId.of("+1000"))
                        .plusDays(3),
                DateTimeUtil.parseEditedDateTimeString("3 days after 25 Apr 8pm +1000", fixedRandomDateTime));


        // relative date respective to another date with time
        // note that this does not work because Natty is not working correctly
        assertNotEqualsIgnoresMilliAndBelow(
                ZonedDateTime.now().withMonth(4).withDayOfMonth(25).withHour(20).withMinute(0).withSecond(0)
                        .plusHours(2),
                DateTimeUtil.parseEditedDateTimeString("2 hours after 25 Apr 8pm", fixedRandomDateTime));
        // TODO
        assertNotEqualsIgnoresMilliAndBelow(
                ZonedDateTime.of(Year.now().getValue(), 4, 25, 20, 0, 0, 0, ZoneId.of("America/Los_Angeles"))
                        .plusDays(5),
                DateTimeUtil.parseEditedDateTimeString("5 days after 8pm PST 25 Apr", fixedRandomDateTime));
    }

    // Extract this for use in other tests
    /**
     * Asserts that two zoned date-times are equal ignoring milliseconds and below.
     */
    private void assertEqualsIgnoresMilliAndBelow(ZonedDateTime expected, ZonedDateTime actual) {
        ChronoUnit truncationUnit = ChronoUnit.SECONDS;
        ZonedDateTime expectedTruncated = expected.truncatedTo(truncationUnit);
        ZonedDateTime actualTruncated = actual.truncatedTo(truncationUnit);
        System.out.println(expectedTruncated);
        System.out.println(actualTruncated);
        // converting to Instant so we compare without caring about timezones
        assertEquals(expectedTruncated.toInstant(), actualTruncated.toInstant());
    }

    /**
     * Asserts that two zoned date-times are not equal ignoring milliseconds and below.
     */
    private void assertNotEqualsIgnoresMilliAndBelow(ZonedDateTime expected, ZonedDateTime actual) {
        ChronoUnit truncationUnit = ChronoUnit.SECONDS;
        ZonedDateTime expectedTruncated = expected.truncatedTo(truncationUnit);
        ZonedDateTime actualTruncated = actual.truncatedTo(truncationUnit);
        // converting to Instant so we compare without caring about timezones
        assertNotEquals(expectedTruncated.toInstant(), actualTruncated.toInstant());
    }
}
