package seedu.taskboss.logic.parser;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.joestelmach.natty.DateGroup;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.task.DateTime;

/*
 * Parses date
 */
public class DateTimeParser {

    private static final String START_DATE = "START DATE: ";
    private static final String END_DATE = "END DATE: ";
    private static final String ERROR_INVALID_DATE = "Failed to understand given date.";
    private static final String ERROR_MULTIPLE_DATES = "Please only enter a single date.";
    private static final String REGEX_US_DATE = "(\\d{1,2})-(\\d{1,2})-((?:\\d\\d){1,2})";
    private static final String REGEX_NON_US_DATE = "$2-$1-$3";

    private com.joestelmach.natty.Parser nattyParser;

    public DateTimeParser() {
        this.nattyParser = new com.joestelmach.natty.Parser();
    }

    /**
     * Returns if a given {@code String date} is a valid date input
     * Used in DateTime class
     */
    public boolean isParseable(String date) {
        List<DateGroup> tempDateGroupList = this.nattyParser.parse(date);
        int numDates = countDates(tempDateGroupList);

        return numDates >= 1;
    }

    public List<DateGroup> parse(String date) {
        String currentDate = date;
        if (Locale.getDefault().equals(Locale.US)) {
            currentDate = date.replaceAll(REGEX_US_DATE, REGEX_NON_US_DATE);
        }

        List<DateGroup> dateGroupList = this.nattyParser.parse(currentDate);
        return dateGroupList;
    }

    /**
     * Parses a given {@code String date}
     * @return parsed DateTime object
     */
    public DateTime parseDate(String date) throws IllegalValueException {
        List <DateGroup> dateGroupList = parse(date);
        int numDates = countDates(dateGroupList);

        if (numDates == 0 && !date.equals("")) {
            throw new IllegalValueException(ERROR_INVALID_DATE);
        } else if (numDates > 1) {
            throw new IllegalValueException(ERROR_MULTIPLE_DATES);
        }

        DateGroup dateGroup = dateGroupList.get(0);

        return new DateTime(dateGroup.getDates().get(0), dateGroup.isDateInferred(), dateGroup.isTimeInferred());
    }

    // MAY BE REMOVING
    public DateTime parseStartDate(String rawStartDate) throws IllegalValueException {
        List<DateGroup> dateGroupList = parse(rawStartDate);
        int numDates = countDates(dateGroupList);

        if (numDates == 0) {
            throw new IllegalValueException(START_DATE + ERROR_INVALID_DATE);
        }

        if (numDates > 1) {
            throw new IllegalValueException(START_DATE + ERROR_MULTIPLE_DATES);
        }

        DateGroup dateGroup = dateGroupList.get(0);

        return new DateTime(dateGroup.getDates().get(0), dateGroup.isDateInferred(), dateGroup.isTimeInferred());

    }

    // MAY BE REMOVING
    public DateTime parseEndDate(String rawEndDate) throws IllegalValueException {

        List<DateGroup> dateGroupList = parse(rawEndDate);
        int numDates = countDates(dateGroupList);

        if (numDates == 0) {
            throw new IllegalValueException(END_DATE + ERROR_INVALID_DATE);
        }

        if (numDates > 1) {
            throw new IllegalValueException(END_DATE + ERROR_MULTIPLE_DATES);
        }

        DateGroup dateGroup = dateGroupList.get(0);

        return new DateTime(dateGroup.getDates().get(0), dateGroup.isDateInferred(), dateGroup.isTimeInferred());

    }

    public Optional<String> convert(Optional<DateTime> dateTime) {
        return dateTime.isPresent() ? Optional.of(dateTime.toString()) : Optional.empty();
    }

    private int countDates(List<DateGroup> dateGroups) {
        int numTotalDates = 0;
        for (DateGroup dateGroup : dateGroups) {
            numTotalDates += dateGroup.getDates().size();
        }
        return numTotalDates;
    }

    // for testing
    public static String getStartDateInvalidDateError() {
        return START_DATE + ERROR_INVALID_DATE;
    }

    // for testing
    public static String getStartDateMultipleDatesError() {
        return START_DATE + ERROR_MULTIPLE_DATES;
    }

    // for testing
    public static String getEndDateInvalidDateError() {
        return END_DATE + ERROR_INVALID_DATE;
    }

    // for testing
    public static String getEndDateMultipleDatesError() {
        return END_DATE + ERROR_MULTIPLE_DATES;
    }
}
