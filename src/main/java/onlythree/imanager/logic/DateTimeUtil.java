package onlythree.imanager.logic;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.antlr.runtime.tree.Tree;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import onlythree.imanager.commons.core.DateTimeFormats;
import onlythree.imanager.commons.exceptions.IllegalValueException;

//@@author A0140023E
/**
 * Contains utility methods used for handling natural-language date-times using Natty.
 */
public class DateTimeUtil {

    private static Parser dateTimeParser = new Parser(TimeZone.getTimeZone(DateTimeFormats.SYSTEM_TIME_ZONE));

    private static final String NATTY_TOKEN_DATE_TIME_ALTERNATIVE = "DATE_TIME_ALTERNATIVE";
    private static final String NATTY_TOKEN_DATE_TIME = "DATE_TIME";
    private static final String NATTY_TOKEN_RELATIVE_DATE = "RELATIVE_DATE";
    private static final String NATTY_TOKEN_RELATIVE_TIME = "RELATIVE_TIME";

    public static final String MESSAGE_NOT_VALID_DATE_TIME = "%1$s is not a valid date/time.";
    public static final String MESSAGE_MULTIPLE_DATE_TIMES_FOUND =
            "Multiple date/times found when expecting only one date from %1$s";
    public static final String MESSAGE_MULTIPLE_DATE_TIME_ALTERNATIVES_FOUND =
            "Date/time alternatives found from %1$s when only one date/time is expected";
    public static final String MESSAGE_RECURRING_DATE_TIME_FOUND =
                    "Recurring date/times are not supported. Found from %1$s";

    public static void initializeNatty() {
        // TODO find a better way to do initialize Natty
        // Hack: Initialize Natty by parsing a valid date-time string
        // because some startup time is required for Natty
        dateTimeParser.parse("tmr");
    }

    /**
     * Parses Date strings into a {@code ZonedDateTime}.
     */
    public static ZonedDateTime parseDateTimeString(String dateTime) throws IllegalValueException {
        DateGroup dateGroup = parseDateTimeStringHelper(dateTime);
        // the date group returned should contain one and only one date.
        assert dateGroup.getDates() != null && dateGroup.getDates().size() == 1;

        final Date date = dateGroup.getDates().get(0);
        // Convert the old java.util.Date class to the much better new classes in java.time package
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(date.toInstant(), DateTimeFormats.SYSTEM_TIME_ZONE);
        return zonedDateTime;
    }

    public static ZonedDateTime parseEditedDateTimeString(String dateTime, ZonedDateTime previousDateTime)
            throws IllegalValueException {

        DateGroup dateGroup = parseDateTimeStringHelper(dateTime);
        // the date group returned should contain one and only one date.
        assert dateGroup.getDates() != null && dateGroup.getDates().size() == 1;

        final Date date = dateGroup.getDates().get(0);

        String dateTimeType = getDateTimeType(dateGroup.getSyntaxTree());

        Date newDate;
        if (dateTimeType.equals(NATTY_TOKEN_RELATIVE_DATE) || dateTimeType.equals(NATTY_TOKEN_RELATIVE_TIME)) {
            // Relative date should be parsed relative to the current date, which has been parsed previously
            // special cases such as 2 days after 25 Apr also works
            // but cases such as 2 hours after 25 Apr 8pm does not work
            newDate = date;
        } else if (hasDateAndTimeSpecified(dateGroup)) {
            // date and time already clearly specified so no need to parse relative to previous date-time
            newDate = date;
        } else {
            newDate = parseDateTimeUsingPrevious(dateGroup, previousDateTime);
        }
        // Convert the old java.util.Date class to the much better new classes in java.time package
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(newDate.toInstant(), DateTimeFormats.SYSTEM_TIME_ZONE);
        return zonedDateTime;
    }

    /**
     * Returns the type of the date-time, e.g. whether it is an explicit date, relative date, or relative time.
     */
    private static String getDateTimeType(Tree dateTimeAlternativeRoot) {
        assert dateTimeAlternativeRoot != null;

        assert dateTimeAlternativeRoot.getText().equals(NATTY_TOKEN_DATE_TIME_ALTERNATIVE)
        && dateTimeAlternativeRoot.getChildCount() == 1;

        Tree dateTimeSubtree = dateTimeAlternativeRoot.getChild(0);
        assert dateTimeSubtree.getText().equals(NATTY_TOKEN_DATE_TIME)
        && dateTimeSubtree.getChildCount() >= 1;

        // note that we only return the first child of the date-time subtree because it is enough to determine
        // the date-type due to the way Natty parses dates. However, this is a brittle approach but there seems
        // to be no better way to do this.
        Tree dateTimeTypeSubtree = dateTimeSubtree.getChild(0);
        return dateTimeTypeSubtree.getText();
    }

    /**
     * Returns a new date-time using the previous date or time combined with the date or time component extracted.
     */
    private static Date parseDateTimeUsingPrevious(DateGroup previousDateGroup, ZonedDateTime previousDateTime)
            throws IllegalValueException {

        String extractedDateTime = extractComponentFromDateTime(previousDateGroup);
        DateGroup newDateGroup = parseDateTimeStringUsingPreviousHelper(extractedDateTime, previousDateTime);

        // the date group returned should contain one and only one date.
        assert newDateGroup.getDates() != null && newDateGroup.getDates().size() == 1;

        Date newDate = newDateGroup.getDates().get(0);
        return newDate;
    }

    /**
     * Extracts the date or time component from the date-time given and format it as a string. The
     * date component will be extracted if only date is specified and the time component will be extracted
     * if only time is specified.
     */
    private static String extractComponentFromDateTime(DateGroup dateGroup) {
        // the date group given should contain one and only one date.
        assert dateGroup.getDates() != null && dateGroup.getDates().size() == 1;
        // The date and time cannot be both inferred as that means nothing is specified at all
        assert !(dateGroup.isDateInferred() && dateGroup.isTimeInferred());
        // if both date and time is specified we cannot extract any components
        assert !hasDateAndTimeSpecified(dateGroup);

        final Date date = dateGroup.getDates().get(0);

        final boolean hasOnlyTimeSpecified = dateGroup.isDateInferred();
        if (hasOnlyTimeSpecified) {
            // note Natty does not support some timezones so we use offset but this means timezone info
            // such as daylight saving time adjustments may be lost
            // milliseconds are also also ignored as Natty does not parse them
            return new SimpleDateFormat("HH:mm:ss Z").format(date);
        }

        final boolean hasOnlyDateSpecified = dateGroup.isTimeInferred();
        if (hasOnlyDateSpecified) {
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        }

        throw new AssertionError("An extracted date/time component should have been returned.");
    }

    private static boolean hasDateAndTimeSpecified(DateGroup dateGroup) {
        return !dateGroup.isDateInferred() && !dateGroup.isTimeInferred();
    }

    private static DateGroup parseDateTimeStringUsingPreviousHelper(String dateTime,
            ZonedDateTime previousDateTime) throws IllegalValueException {
        // Convert back to old java.util.Date class for use in Natty
        Date previousDateTimeAsOldDateClass = Date.from(previousDateTime.toInstant());
        List<DateGroup> dateGroups = dateTimeParser.parse(dateTime, previousDateTimeAsOldDateClass);

        checkForSingleDateGroup(dateGroups, dateTime);

        final DateGroup dateGroup = dateGroups.get(0);

        // rejects recurring dates as they implicitly means it's more than one date
        if (dateGroup.isRecurring()) {
            throw new IllegalValueException(String.format(MESSAGE_RECURRING_DATE_TIME_FOUND, dateTime));
        }

        checkForSingleDateAlternative(dateGroup, dateTime);

        // returns the date group that represents the date-time and extra information about it
        return dateGroup;
    }

    /**
     * Returns a DateGroup representing the date-time with extra information about it.
     */
    private static DateGroup parseDateTimeStringHelper(String dateTime) throws IllegalValueException {
        final List<DateGroup> dateGroups = dateTimeParser.parse(dateTime);

        checkForSingleDateGroup(dateGroups, dateTime);

        final DateGroup dateGroup = dateGroups.get(0);

        // rejects recurring dates as they implicitly means it's more than one date
        if (dateGroup.isRecurring()) {
            throw new IllegalValueException(String.format(MESSAGE_RECURRING_DATE_TIME_FOUND, dateTime));
        }

        checkForSingleDateAlternative(dateGroup, dateTime);

        // returns the date group that represents the date-time and extra information about it
        return dateGroup;
    }

    private static void checkForSingleDateGroup(List<DateGroup> dateGroups, String dateTime)
            throws IllegalValueException {

        if (dateGroups.size() == 0) {
            throw new IllegalValueException(String.format(MESSAGE_NOT_VALID_DATE_TIME, dateTime));
        }

        if (dateGroups.size() > 1) {
            throw new IllegalValueException(String.format(MESSAGE_MULTIPLE_DATE_TIMES_FOUND, dateTime));
        }

        assert dateGroups.size() == 1;
    }

    private static void checkForSingleDateAlternative(DateGroup dateGroup, String dateTime)
            throws IllegalValueException {

        final List<Date> dateAlternatives = dateGroup.getDates();

        // if there is at least one date group, there should always be at least one date.
        // Therefore, if the assertion fail there might be a bug in Natty.
        assert dateAlternatives.size() >= 1;

        if (dateAlternatives.size() > 1) {
            throw new IllegalValueException(String.format(MESSAGE_MULTIPLE_DATE_TIME_ALTERNATIVES_FOUND, dateTime));

        }

        assert dateAlternatives.size() == 1;
    }

    /**
     * Returns true if a String contains only a single date-time string parseable by Natty, otherwise returns false.
     */
    public static boolean isSingleDateTimeString(String dateTime) {
        final List<DateGroup> dateGroups = dateTimeParser.parse(dateTime);


        if (!isSingleDateGroup(dateGroups)) {
            return false;
        }

        DateGroup dateGroup = dateGroups.get(0);

        // rejects recurring dates as they implicitly means it's more than one date
        if (dateGroup.isRecurring()) {
            return false;
        }

        if (!isSingleDateAlternative(dateGroup)) {
            return false;
        }

        return true;
    }

    private static boolean isSingleDateGroup(List<DateGroup> dateGroups) {
        if (dateGroups.size() == 0) {
            return false;
        }

        // Example: "Wed ~ Thur" will result in 2 date groups
        if (dateGroups.size() > 1) {
            return false;
        }

        assert dateGroups.size() == 1;
        return true;
    }

    private static boolean isSingleDateAlternative(DateGroup dateGroup) {
        final List<Date> dateAlternatives = dateGroup.getDates();

        // if there is at least one date group, there should always be at least one date.
        // Therefore, if the assertion fail there might be a bug in Natty.
        assert dateAlternatives.size() >= 1;

        // Example: "Wed or Thur" will result in 2 date alternatives
        if (dateAlternatives.size() > 1) {
            return false;
        }

        assert dateAlternatives.size() == 1;
        return true;
    }
}
