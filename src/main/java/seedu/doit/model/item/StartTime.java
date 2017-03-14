package seedu.doit.model.item;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.logic.parser.DateTimeParser;

/**
 * Represents a Item's start time in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartTime(String)}
 */
public class StartTime {

    public static final String MESSAGE_STARTTIME_CONSTRAINTS =
        "Item Start Time should be 2 alphanumeric/period strings separated by '@'";
    public static final String STARTTIME_VALIDATION_REGEX =
        "^(0[0-9]||1[0-2])/([0-2][0-9]||3[0-1])/([0-9][0-9])?[0-9][0-9] [0-2]\\d:[0-6]\\d$";

    public final String value;

    /**
     * Validates given startTime.
     *
     * @throws IllegalValueException if given startTime string is invalid.
     */
    public StartTime(String startTime) throws IllegalValueException {
        assert startTime != null;
        String trimmedStartTime = startTime.trim();

        LocalDateTime date = DateTimeParser.parseDateTime(trimmedStartTime).orElseThrow(()
            -> new IllegalValueException("Invalid Date Format: " + trimmedStartTime));

        String dateInString = formatDate(date);

        if (!isValidStartTime(dateInString)) {
            throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
        }

        this.value = dateInString;
    }

    /**
     * Returns if a given string is a valid task start time.
     */
    public static boolean isValidStartTime(String test) {
        return test.matches(STARTTIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof StartTime
            && this.value.equals(((StartTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    private static String formatDate(LocalDateTime input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm");
        return input.format(formatter);
    }

}
