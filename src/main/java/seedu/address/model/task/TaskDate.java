package seedu.address.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class TaskDate {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Task date should be in the format dd/MM/yyyy";
//    public static final String EMAIL_VALIDATION_REGEX = "\\d+";
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    public final String value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public TaskDate(String date) throws IllegalValueException {
        if (date != null) {
            String trimmedDate = date.trim();
            if (!isValidDate(trimmedDate)) {
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
            }
            this.value = trimmedDate;
        } else {
            this.value = null;
        }
    }

    /**
     * Returns if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);
        try {
            //throws ParseException if date is not valid
            sdf.parse(test);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this.value == null) {
            return false;
        }
        return other == this // short circuit if same object
                || (other instanceof TaskDate // instanceof handles nulls
                && this.value.equals(((TaskDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
