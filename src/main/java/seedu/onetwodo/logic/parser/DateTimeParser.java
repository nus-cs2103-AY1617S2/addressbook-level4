package seedu.onetwodo.logic.parser;

import static seedu.onetwodo.model.task.Date.MESSAGE_DATE_INPUT_CONSTRAINTS;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.onetwodo.commons.exceptions.IllegalValueException;

//@@author A0139343E
/**
 * Use for parsering dates from user's input via natty library.
 *
 */
public class DateTimeParser {

    public DateTimeParser() {}

    //private static Parser parser = new Parser();

    /**
     * Parse user input using Natty library.
     *
     * @param input trimmed user's input for date & time.
     * @param defaultDateTime if no date OR time is stated, use this value.
     * @return LocalDateTime if Natty parses successfully. Else, return empty Optional.
     */
    public static Optional<LocalDateTime> parseDateTime(String input, LocalDateTime defaultDateTime)
                throws IllegalValueException {

        if (input.isEmpty()) {
            return Optional.empty();
        }

        // Input exist. Try to parse it using Natty.
        Parser parser = new Parser();

        // parse() is a method from natty.
        List<DateGroup> dateGroups = parser.parse(input);
        if (dateGroups.isEmpty()) {
            throw new IllegalValueException(MESSAGE_DATE_INPUT_CONSTRAINTS);
        }
        DateGroup dateGroup = dateGroups.get(0);
        List<Date> dates = dateGroup.getDates();

        if (dates.isEmpty()) {
            throw new IllegalValueException(MESSAGE_DATE_INPUT_CONSTRAINTS);
        }
        Date parsedDateTime = dates.get(0);
        LocalDateTime localDateTime = toLocalDateTime(parsedDateTime);
        LocalDateTime finalisedDateTime = toFinalDateTime(localDateTime, defaultDateTime, dateGroup);
        LocalDateTime formattedFinalisedDateTime = finalisedDateTime.withSecond(0).withNano(0);

        return Optional.of(formattedFinalisedDateTime);
    }

    /**
     * Convert a EndDate represented in UTC to local machine representation.
     *
     * @param dateTime the date and time that Natty uses.
     * @return local machine date and time.
     */
    private static LocalDateTime toLocalDateTime (Date dateTime) {
        Instant instant = Instant.ofEpochMilli(dateTime.getTime());
        ZoneId zone = ZoneOffset.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }

    /**
     * Fill in the missing information that user missed out. It can be date OR time input.
     *
     * @param localDateTime the local date and time that Natty thinks the user might want.
     * @param defaultDateTime to be used to replace missing date/time
     * @param dateGroup Natty parsed output.
     * @return final version of LocalDateTime that user might want specifically.
     */
    private static LocalDateTime toFinalDateTime (LocalDateTime localDateTime,
                LocalDateTime defaultDateTime, DateGroup dateGroup) {
        LocalDateTime finalDateTime;
        if (dateGroup.isTimeInferred()) {
            finalDateTime = LocalDateTime.of(localDateTime.toLocalDate(), defaultDateTime.toLocalTime());
        } else if (dateGroup.isDateInferred()) {
            finalDateTime = LocalDateTime.of(defaultDateTime.toLocalDate(), localDateTime.toLocalTime());
        } else {
            finalDateTime = localDateTime;
        }

        return finalDateTime;
    }

}
