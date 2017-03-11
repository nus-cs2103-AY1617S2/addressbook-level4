package seedu.typed.model.task;

import seedu.typed.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date number in the task manager. Guarantees: immutable;
 * is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Task date should be in the format DD/MM/YYYY";
    public static final String DATE_VALIDATION_REGEX = "\\d{2}\\/\\d{2}\\/\\d{4}";

    private final String value;

   /**
     * Validates given date.
     * @param date
     * @throws IllegalValueException
     *             if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        if (date == null) {
            this.value = "";
        } else {
            String trimmedDate = date.trim();
            if (!isValidDate(trimmedDate)) {
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
            }
            this.value = trimmedDate;
        }
    }

    /**
     *
     * @param test
     * @return true if a given string is a valid task date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    public String getValue() {
        return this.value;
    }


    /**
     * Returns the date if exists
     * else returns an empty string
     */
    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                        && this.value.equals(((Date) other).getValue())); // state
        // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
