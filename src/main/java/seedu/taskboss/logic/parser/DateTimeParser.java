package seedu.taskboss.logic.parser;

import java.util.List;
import java.util.Locale;

import com.joestelmach.natty.DateGroup;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.task.DateTime;

//@@author A0143157J
/*
 * Parses date and returns a DateTime object for execution
 */
public class DateTimeParser {

    private static final String ERROR_INVALID_DATE = "Failed to understand given date.";
    private static final String ERROR_MULTIPLE_DATES = "Please only enter a single date.";
    private static final String REGEX_US_DATE = "(\\d{1,2})-(\\d{1,2})-((?:\\d\\d){1,2})";
    private static final String REGEX_NON_US_DATE = "$2-$1-$3";
    private static final String EMPTY_STRING = "";

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

    /**
     * Parses a given {@code String date}
     * @return List<DateGroup>
     */
    public List<DateGroup> parse(String date) {
        String currentDate = date;
        if (Locale.getDefault().equals(Locale.US)) {
            currentDate = date.replaceAll(REGEX_US_DATE, REGEX_NON_US_DATE);
        }

        List<DateGroup> dateGroupList = this.nattyParser.parse(currentDate);
        return dateGroupList;
    }

    /**
     * Calls helper parse() to parse a given {@code String date}
     * and verifies accepted date input
     * @return parsed DateTime object
     */
    public DateTime parseDate(String date) throws IllegalValueException {
        if (date.equals("Optional[" + EMPTY_STRING + "]")) {
            return new DateTime(EMPTY_STRING);
        }

        List <DateGroup> dateGroupList = parse(date);
        int numDates = countDates(dateGroupList);

        if (numDates == 0) {
            throw new IllegalValueException(ERROR_INVALID_DATE);
        } else if (numDates > 1) {
            throw new IllegalValueException(ERROR_MULTIPLE_DATES);
        }

        DateGroup dateGroup = dateGroupList.get(0);

        return new DateTime(dateGroup.getDates().get(0), dateGroup.isDateInferred(), dateGroup.isTimeInferred());
    }

    /**
     * Returns number of DateGroup present in a given {@code List<DateGroup>}
     */
    private int countDates(List<DateGroup> dateGroups) {
        int numTotalDates = 0;
        for (DateGroup dateGroup : dateGroups) {
            numTotalDates += dateGroup.getDates().size();
        }
        return numTotalDates;
    }

    // for testing
    public static String getInvalidDateError() {
        return ERROR_INVALID_DATE;
    }

    // for testing
    public static String getMultipleDatesError() {
        return ERROR_MULTIPLE_DATES;
    }
}
