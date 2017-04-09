package seedu.doit.logic.parser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
//@@author A0146809W

/**
 * Parses date & time using natty Parser
 */

public class DateTimeParser {

    /**
     * Parses string input into LocalDateTime object using natty
     *
     * @param input naturally typed date/time with reference to current date
     * @return Optional type of LocalDateTime object, returns optional type of empty if  unable to parse
     */

    private static final String EMPTY_STRING = "";

    public static Optional<LocalDateTime> parseDateTime(String input) {
        Date date = new Date(); //get current date
        Parser dateTimeParser = new Parser();

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

        Date parsedDate = dateGroup.getDates().get(0);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(parsedDate.toInstant(), ZoneId.systemDefault());

        return Optional.of(localDateTime);
    }
}
