package seedu.taskmanager.model.task;

import seedu.taskmanager.commons.exceptions.IllegalValueException;

/**
 * Represents start time of a task in ProcrastiNomore. Guarantees: immutable; is
 * valid as declared in {@link #isValidStartTime(String)}
 */
public class StartTime {

    public static final String EMPTY_FIELD = "EMPTY_FIELD";
    public static final String MESSAGE_STARTTIME_CONSTRAINTS = "Start/End time for task "
            + "should only contain a day (e.g. thursday) " + "or a date with the format: DD/MM/YY (e.g. 03/03/17)\n"
            + "May also include time (e.g. 1400) behind date in some instances\n"
            + "Enter HELP for user guide with detailed explanations of all commands";
    public static final String STARTTIME_VALIDATION_REGEX1 = "([0-1][0-9][0-5][0-9])|(2[0-3][0-5][0-9])";
    // public static final String STARTTIME_VALIDATION_REGEX2 = "[a-zA-Z]+";
    // public static final String STARTTIME_VALIDATION_REGEX3 =
    // "\\d{2}/\\d{2}/\\d{2}";
    // public static final String STARTTIME_VALIDATION_REGEX4 =
    // "(\\d{2}/\\d{2}/\\d{2}) (\\d+)";

    public final String value;

    // @@author A0139520L
    /**
     * Validates given start time.
     *
     * @throws IllegalValueException
     *             if given start time string is invalid.
     */
    public StartTime(String startTime) throws IllegalValueException {
        assert startTime != null;
        String trimmedStartTime = startTime.trim();
        if (!isValidStartTime(trimmedStartTime)) {
            throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
        }
        this.value = trimmedStartTime;
    }

    /**
     * Returns true if a given string is a valid Task start time.
     */
    public static boolean isValidStartTime(String test) {
        return test.matches(STARTTIME_VALIDATION_REGEX1)
                || /*
                    * test.matches(STARTTIME_VALIDATION_REGEX2) ||
                    * test.matches(STARTTIME_VALIDATION_REGEX3) ||
                    * test.matches(STARTTIME_VALIDATION_REGEX4) ||
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
                || (other instanceof StartTime // instanceof handles nulls
                        && this.value.equals(((StartTime) other).value)); // state
        // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
