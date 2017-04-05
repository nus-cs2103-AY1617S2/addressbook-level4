package seedu.taskmanager.model.task;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.commons.util.CurrentDate;

/**
 * Represents date of a task in ProcrastiNomore. Guarantees: immutable; is valid
 * as declared in {@link #isValidDate(String)}
 */
public class StartDate {

    public static final String STARTDATE_VALIDATION_REGEX1 = "\\d{2}/\\d{2}/\\d{2}";
    public static final String STARTDATE_VALIDATION_REGEX2 = "[a-zA-Z]+";
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
    public StartDate(String startDate) throws IllegalValueException {
        assert startDate != null;
        String trimmedStartDate = startDate.trim();
        if (!isValidStartDate(trimmedStartDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.value = trimmedStartDate;
    }

    /**
     * Returns if a given string is a valid task date.
     */
    public static boolean isValidStartDate(String test) {
        return (test.matches(STARTDATE_VALIDATION_REGEX1) && CurrentDate.isValidDate(test))
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
                || (other instanceof StartDate // instanceof handles nulls
                        && this.value.equals(((StartDate) other).value)); // state
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
