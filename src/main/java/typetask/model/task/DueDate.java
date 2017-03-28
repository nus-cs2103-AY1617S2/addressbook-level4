package typetask.model.task;

import typetask.commons.exceptions.IllegalValueException;

/**
 * Represents a task's date in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class DueDate {
    public static final String MESSAGE_DATE_CONSTRAINTS = "Task's date should be in this format DD/MM/YYYY";
    public static final String DATE_VALIDATION_REGEX = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
    //public static final String DATE_VALIDATION_REGEX = "(*)";
    public final String value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public DueDate(String date) throws IllegalValueException {
        assert date != null;
        String trimmedDate = date.trim();
 //       if (!isValidDate(trimmedDate)) {
 //           throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
//        }
        this.value = trimmedDate;
    }

    /**
     * Returns true if a given string is a valid task date.
     */
    public static boolean isValidDate(String test) {
        if (test.equals("")) {
            return true;
        } else {
            return test.matches(DATE_VALIDATION_REGEX);
        }

    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueDate // instanceof handles nulls
                && this.value.equals(((DueDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
