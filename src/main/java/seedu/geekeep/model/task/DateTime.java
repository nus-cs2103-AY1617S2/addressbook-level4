package seedu.geekeep.model.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.geekeep.commons.exceptions.IllegalValueException;

//@@author A0121658E
/**
 * Represents the ending date and time of a task. Guarantees: immutable; is valid as declared in
 * {@link #isValidDateTime()}
 */
public class DateTime {

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Date and time format should be in this format: DD-MM-YY [HHMM]";
    public static final String DATETIME_VALIDATION_REGEX = "\\d{2}-\\d{2}-\\d{2}(\\s{1}\\d{4})?";
    public static final String DATETIME_REGEX_WITHOUT_TIME = "\\d{2}-\\d{2}-\\d{2}";
    public static final String DEFAULT_TIME = "2359";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yy HHmm");

    public final LocalDateTime dateTime;
    public final String value;

    /**
     *  Validates given dateTime.
     */
    public static boolean isValidDateTime(String test) {
        return test.matches(DATETIME_VALIDATION_REGEX);
    }

    public static boolean isValidDateTimeWithoutTime(String test) {
        return test.matches(DATETIME_REGEX_WITHOUT_TIME);
    }

    public DateTime(String dateTimeString) throws IllegalValueException {
        if (!isValidDateTime(dateTimeString)) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        this.value = dateTimeString;
        if (isValidDateTimeWithoutTime(dateTimeString)) {
            dateTimeString = dateTimeString + " " + DEFAULT_TIME;
        }
        this.dateTime = LocalDateTime.parse(dateTimeString, FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                        && this.value.equals(((DateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }

}
