package seedu.taskmanager.model.task;

import seedu.taskmanager.commons.exceptions.IllegalValueException;

/**
 * Represents date of a task in ProcrastiNomore. Guarantees: immutable; is valid
 * as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String DATE_VALIDATION_REGEX1 = "\\d{2}/\\d{2}/\\d{2}";
    public static final String DATE_VALIDATION_REGEX2 = "[a-zA-Z]+";
    public static final String EMPTY_FIELD = "EMPTY_FIELD";
    public static final String MESSAGE_DATE_CONSTRAINTS = "Task date should be either "
            + "a day (e.g. thursday) or a date with the format: DD/MM/YY (e.g. 03/03/17)\n"
            + "May also include time (e.g. 1400) behind date \n"
            + "Enter HELP for user guide with detailed explanations of all commands";

    public final String value;

    // @@author A0139520L
    /**
     * Validates given date.
     *
     * @throws IllegalValueException
     *             if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        assert date != null;
        String trimmedDate = date.trim();
        if (!isValidDate(trimmedDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.value = trimmedDate;
    }

    /**
     * Returns if a given string is a valid task date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX1) || test.matches(DATE_VALIDATION_REGEX2)
                || test.matches(EMPTY_FIELD);
    }

    // @@author
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                        && this.value.equals(((Date) other).value)); // state
        // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
