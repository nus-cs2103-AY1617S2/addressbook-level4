package seedu.geekeep.model.task;

import java.time.LocalDateTime;

import seedu.geekeep.commons.exceptions.IllegalValueException;

/**
 * Represents the ending date and time of a task. Guarantees: immutable; is valid as declared in
 * {@link #isValidDateTime()}
 */
public class DateTime {

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Date and time format should be in this format: YYYY-MM-DDTHH:MM:SS";
    public static final String DATETIME_VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}";

    /**
     *  Validates given dateTime.
     */
    public static boolean isValidDateTime(String test) {
        return test.matches(DATETIME_VALIDATION_REGEX);
    }

    public final LocalDateTime dateTime;
    public final String value;

    public DateTime(String dateTimeString) throws IllegalValueException {
        if (!isValidDateTime(dateTimeString)) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        this.value = dateTimeString;
        this.dateTime = LocalDateTime.parse(dateTimeString);
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
