package seedu.opus.logic.parser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

//@@author A0126345J
/**
 * Parses user date input.
 */
public class DateTimeParser {

    /**
     * Parses user date input into LocalDateTime object.
     *
     * @param dateString user input date string
     * @return an Optional that is either empty or contains a parsed LocalDateTime object
     */
    public static Optional<LocalDateTime> parse(String dateString) {

        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(dateString);
        Date date = null;

        for (DateGroup group:groups) {
            date = group.getDates().get(0);
        }

        if (date == null) {
            return Optional.empty();
        }

        LocalDateTime output = convertDateToLocalDateTime(date);
        return Optional.ofNullable(output);
    }

    public static Optional<DateGroup> parseDateGroup(String dateString) {
        Parser dateTimeParser = new Parser();
        List<DateGroup> groups = dateTimeParser.parse(dateString);

        if (groups.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(groups.get(0));
    }

    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
