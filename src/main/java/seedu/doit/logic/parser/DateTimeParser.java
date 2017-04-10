package seedu.doit.logic.parser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.doit.commons.exceptions.IllegalValueException;
//@@author A0146809W

/**
 * Parses date & time using natty Parser
 */

public class DateTimeParser {
    private static final String MESSAGE_TIME_CONSTRAINTS = "Date/Time should be in "
        + "MM-DD-YY HH:MM Format or relative date today, tomorrow, next wednesday";

    private static final String DAY_MONTH_SWITCH_REGEX = "^(\\d+/)(\\d+/)(\\d+.*)";

    /**
     * Parses string input into LocalDateTime object using natty
     *
     * @param input naturally typed date/time with reference to current date
     * @return Optional type of LocalDateTime object, returns optional type of empty if  unable to parse
     */

    private static final String EMPTY_STRING = "";

    public static Optional<LocalDateTime> parseDateTime(String input) throws IllegalValueException {
        Date date = new Date(); //get current date
        Parser dateTimeParser = new Parser();

        if (input.matches(DAY_MONTH_SWITCH_REGEX)) {
            //changes format from dd/mm/yy to mm/dd/yy as natty can only parse in mm/dd/yy format
            input = switchDayToMonth(input);
        }

        if (input.equals(EMPTY_STRING)) {
            return Optional.empty();
        }

        List<DateGroup> groupsOfDateGroup = dateTimeParser.parse(input, date);

        if (groupsOfDateGroup.isEmpty()) {
            return Optional.empty();
        }

        DateGroup dateGroup = groupsOfDateGroup.get(0);

        if (dateGroup.getDates().isEmpty()) {
            return Optional.empty();
        }

        //checks if natty parses all the input, if it does not, it means there is invalid input after the valid time
        //or another date/time
        if (!dateGroup.getFullText().equals(dateGroup.getText())) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }

        Date parsedDate = dateGroup.getDates().get(0);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(parsedDate.toInstant(), ZoneId.systemDefault());

        return Optional.of(localDateTime);
    }
    /**
     * Switch the day and month location of the date String
     *
     * @param input A String in DD/MM/YY format.
     * @return      a String in MM/DD/YY.
     */
    private static String switchDayToMonth(String input) {
        Pattern dayMonthSwitchPattern = Pattern.compile(DAY_MONTH_SWITCH_REGEX);
        Matcher matcher = dayMonthSwitchPattern.matcher(input);

        matcher.matches(); //to ensure output for matcher.group()

        return matcher.group(2) + matcher.group(1) + matcher.group(3);
    }
}
