package seedu.doit.model.item;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.logic.parser.DateTimeParser;

/**
 * Represents a Item's end time in the item manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime {

    public static final String MESSAGE_ENDTIME_CONSTRAINTS =
        "Item End Time should be 2 alphanumeric/period strings separated by '@'";
    public static final String ENDTIME_VALIDATION_REGEX =
        "^([0-9]||0[0-9]||1[0-2])/([0-2][0-9]||3[0-1])/([0-9][0-9])?[0-9][0-9] [0-2]\\d:[0-6]\\d$";


    public final String value;

    /**
     * Validates given endTime.
     *
     * @throws IllegalValueException if given endTime string is invalid.
     */
    public EndTime(String endTime) throws IllegalValueException {
        assert endTime != null;
        String trimmedEndTime = endTime.trim();

        LocalDateTime date = DateTimeParser.parseDateTime(trimmedEndTime).orElseThrow(()
            -> new IllegalValueException("Invalid Date Format: " + trimmedEndTime));

        String dateInString = formatDate(date);

        if (!isValidEndTime(dateInString)) {
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        }
        this.value = dateInString;
    }

    /**
     * Returns if a given string is a valid item end time.
     */
    public static boolean isValidEndTime(String test) {
        return test.matches(ENDTIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof EndTime // instanceof handles nulls
            && this.value.equals(((EndTime) other).value)); // state check
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
