package seedu.taskmanager.model.task;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.commons.util.DateTimeUtil;

/**
 * Represents date of a task in ProcrastiNomore. Guarantees: immutable; is valid
 * as declared in {@link #isValidDate(String)}
 */
public class EndDate {

    public static final String ENDDATE_VALIDATION_REGEX1 = "\\d{2}/\\d{2}/\\d{2}";
    public static final String ENDDATE_VALIDATION_REGEX2 = "[a-zA-Z]+";
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
    public EndDate(String endDate) throws IllegalValueException {
        assert endDate != null;
        String trimmedEndDate = endDate.trim();
        if (!isValidEndDate(trimmedEndDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.value = trimmedEndDate;
    }

    /**
     * Returns if a given string is a valid task date.
     */
    public static boolean isValidEndDate(String test) {
        return (test.matches(ENDDATE_VALIDATION_REGEX1) && DateTimeUtil.isValidDate(test)) || test.matches(EMPTY_FIELD);
    }

    // @@author
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndDate // instanceof handles nulls
                        && this.value.equals(((EndDate) other).value)); // state
        // check
    }

    /*
     * public boolean laterThan(StartDate other) { if
     * ((this.value).compareTo(other.value) > 0) { return true; } return false;
     * }
     *
     * public boolean laterThan(EndDate other) { if
     * ((this.value).compareTo(other.value) > 0) { return true; } return false;
     * }
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
