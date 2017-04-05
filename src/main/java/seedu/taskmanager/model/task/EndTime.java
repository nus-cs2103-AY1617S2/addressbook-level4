package seedu.taskmanager.model.task;

import seedu.taskmanager.commons.exceptions.IllegalValueException;

/**
 * Represents endtime of a task in ProcrastiNomore. Guarantees: immutable; is
 * valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime {

    public static final String EMPTY_FIELD = "EMPTY_FIELD";
    public static final String ENDTIME_VALIDATION_REGEX1 = "([0-1][0-9][0-5][0-9])|(2[0-3][0-5][0-9])";
    // public static final String ENDTIME_VALIDATION_REGEX2 = "[a-zA-Z]+";
    // public static final String ENDTIME_VALIDATION_REGEX3 =
    // "\\d{2}/\\d{2}/\\d{2}";
    // public static final String ENDTIME_VALIDATION_REGEX4 =
    // "(\\d{2}/\\d{2}/\\d{2}) (\\d+)";
    public static final String MESSAGE_ENDTIME_CONSTRAINTS = "Start/End time for task should only "
            + "contain a day (e.g. thursday) or a date with the format: DD/MM/YY (e.g. 03/03/17)\n"
            + "May also include time (e.g. 1400) behind date in some instances\n"
            + "Enter HELP for user guide with detailed explanations of all commands";

    public final String value;

    // @@author A0139520L
    /**
     * Validates given end time.
     *
     * @throws IllegalValueException
     *             if given end time string is invalid.
     */
    public EndTime(String endTime) throws IllegalValueException {
        assert endTime != null;
        String trimmedEndTime = endTime.trim();
        if (!isValidEndTime(trimmedEndTime)) {
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        }
        this.value = trimmedEndTime;
    }

    /**
     * Returns true if a given string is a valid Task end time.
     */
    public static boolean isValidEndTime(String test) {
        return test.matches(ENDTIME_VALIDATION_REGEX1)
                || /*
                    * test.matches(ENDTIME_VALIDATION_REGEX2) ||
                    * test.matches(ENDTIME_VALIDATION_REGEX3) ||
                    * test.matches(ENDTIME_VALIDATION_REGEX4) ||
                    */ test.matches(EMPTY_FIELD);
    }

    // @@author
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                        && this.value.equals(((EndTime) other).value)); // state
        // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
