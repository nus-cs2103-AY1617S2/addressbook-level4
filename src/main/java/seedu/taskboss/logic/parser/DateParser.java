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
public class DateParser {

    private static final String START_DATE = "START DATE: ";
    private static final String END_DATE = "END DATE: ";
    private static final String ERROR_INVALID_DATE = "Failed to understand given date.";
    private static final String ERROR_MULTIPLE_DATES = "Please only enter a single date.";

    private com.joestelmach.natty.Parser nattyParser;

    public DateParser() {
        this.nattyParser = new com.joestelmach.natty.Parser();
    }

    public DateTime parseStartDate(String rawStartDate) throws IllegalValueException {

        String currentStartDate = rawStartDate;
        if (Locale.getDefault().equals(Locale.US)) {
            currentStartDate = rawStartDate.replaceAll("(\\d{1,2})-(\\d{1,2})-((?:\\d\\d){1,2})", "$2-$1-$3");
        }

        List<DateGroup> dateGroupList = this.nattyParser.parse(currentStartDate);
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

    public DateTime parseEndDate(String rawEndDate) throws IllegalValueException {

        String currentEndDate = rawEndDate;
        if (Locale.getDefault().equals(Locale.US)) {
            currentEndDate = rawEndDate.replaceAll("(\\d{1,2})-(\\d{1,2})-((?:\\d\\d){1,2})", "$2-$1-$3");
        }

        List<DateGroup> dateGroupList = this.nattyParser.parse(currentEndDate);
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

    public static String getStartDateInvalidDateError() {
        return START_DATE + ERROR_INVALID_DATE;
    }

    public static String getStartDateMultipleDatesError() {
        return START_DATE + ERROR_MULTIPLE_DATES;
    }

    public static String getEndDateInvalidDateError() {
        return END_DATE + ERROR_INVALID_DATE;
    }

    public static String getEndDateMultipleDatesError() {
        return END_DATE + ERROR_MULTIPLE_DATES;
    }
}
