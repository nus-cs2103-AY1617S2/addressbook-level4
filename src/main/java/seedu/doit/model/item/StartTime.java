package seedu.doit.model.item;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.logic.parser.DateTimeParser;

//@@author A0139399J
/**
 * Represents a Item's start time in the task manager. Guarantees: immutable; is
 * valid as declared in {@link #isValidStartTime(String)}
 */
public class StartTime implements Comparable<StartTime> {

    public static final String NO_START_TIME = null;
    public static final String MESSAGE_STARTTIME_CONSTRAINTS = "Item Start Time should be "
        + "2 alphanumeric/period strings separated by '@'";
    public static final String STARTTIME_VALIDATION_REGEX = "^([0-9]||0[0-9]||1[0-2])/([0-2][0-9]||3[0-1])"
        + "/([0-9][0-9])?[0-9][0-9] [0-2]\\d:[0-6]\\d$";

    public final String value;

    /**
     * Gives a NO_START_TIME which represents there is no start time.
     */
    public StartTime() {
        this.value = NO_START_TIME;
    }

    /**
     * Validates given startTime.
     *
     * @throws IllegalValueException if given startTime string is invalid.
     */
    public StartTime(String startTime) throws IllegalValueException {
        if (startTime == NO_START_TIME) {
            this.value = NO_START_TIME;
        } else {
            String trimmedStartTime = startTime.trim();

            LocalDateTime date = DateTimeParser.parseDateTime(trimmedStartTime)
                .orElseThrow(() -> new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS));

            String dateInString = formatDate(date);

            if (!isValidStartTime(dateInString)) {
                throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
            }

            this.value = dateInString;
        }
    }

    /**
     * Returns if a given string is a valid task start time.
     */
    public static boolean isValidStartTime(String test) {
        return test.matches(STARTTIME_VALIDATION_REGEX);
    }

    private static String formatDate(LocalDateTime input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm");
        return input.format(formatter);

    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        return (other == this // short circuit if same object
            ) || ((other instanceof StartTime// instanceof handles nulls
            ) && this.value.equals(((StartTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public int compareTo(StartTime other) {
        return this.value.compareTo(other.value);
    }

}
