package seedu.ezdo.logic.parser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

/*
 * Manipulates a given date from user's input for Natty dependency.
 * The output after Natty dependency is done is converted back to the date format by the user.
 */
public class DateParser {

    public static final String USER_DATE_OUTPUT_FORMAT = "dd/MM/yyyy HH:mm";
    public static final String DAY_MONTH_YEAR_FORMAT = "^([0-9]{1,2}[-/])([0-9]{1,2}[-/])([0-9]{4}.*)";
    public static final SimpleDateFormat USER_OUTPUT_DATE_FORMAT = new SimpleDateFormat(USER_DATE_OUTPUT_FORMAT);

    public static final int MATCH_DAY_INDEX = 1;
    public static final int MATCH_MONTH_INDEX = 2;
    public static final int MATCH_YEAR_INDEX = 3;

    public String value;

    //@@author A0138907W
    /**
     * Converts the date format from from user's input for Natty dependency.
     */
    public DateParser(String input) {
        // swap day and month as natty only accepts the MM/DD/YYYY format
        if (input.matches(DAY_MONTH_YEAR_FORMAT)) {
            input = swapDayAndMonth(input);
        }
        value = nattyManipulation(input);
    }

    /**
     * Swaps the day and month of a date in DD/MM/YYYY format for natty to parse.
     *
     * @param input A string representing a date in the DD/MM/YYYY format.
     * @return      A string equal to the input date but in MM/DD/YYYY format.
     */
    private String swapDayAndMonth(String input) {
        Pattern dayMonthYearPattern = Pattern.compile(DAY_MONTH_YEAR_FORMAT);
        Matcher matcher = dayMonthYearPattern.matcher(input);

        matcher.matches();
        return matcher.group(MATCH_MONTH_INDEX) + matcher.group(MATCH_DAY_INDEX) + matcher.group(MATCH_YEAR_INDEX);
    }

    //@@author A0139177W
    /**
     * Uses Natty dependency (natural language date parser) to manipulate date
     * input in String.
     * @throws IndexOutOfBoundsException when taskDate cannot be manipulated because
     * taskDate is optional or taskDate is not a valid NLP command.
     */
    private String nattyManipulation(String taskDate) {
        try {
            // Initialises Natty parser
            Parser parser = new Parser();

            // Parses input String into a list of DateGroups
            List<DateGroup> dateGroupList = parser.parse(taskDate);

            // Retrieves parsed date
            Date parsedDate = retrieveParsedDate(dateGroupList);

            // Format parsed date to suit the UI
            return USER_OUTPUT_DATE_FORMAT.format(parsedDate);

        } catch (IndexOutOfBoundsException e) {
            return taskDate;
        }
    }

    /**
     * Returns the parsed date from dateGroupList
     * @param dateGroupList  A list of DateGroup which contains a list of Dates.
     * @return parsedDate    The parsed date.
     */
    private Date retrieveParsedDate(List<DateGroup> dateGroupList) {
        int firstElement = 0;
        DateGroup parsedDateGroup = dateGroupList.get(firstElement);
        List<Date> parsedDateList = parsedDateGroup.getDates();
        Date parsedDate = parsedDateList.get(firstElement);
        return parsedDate;
    }
    //@@author
}
