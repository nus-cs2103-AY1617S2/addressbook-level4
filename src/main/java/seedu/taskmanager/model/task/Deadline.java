package seedu.taskmanager.model.task;

import seedu.taskmanager.commons.exceptions.IllegalValueException;

/**
 * Represents time of a task in ProcrastiNomore.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "Task deadline should be either a day (e.g. thursday) or a date with the format: DD/MM/YY (e.g. 03/03/17)";
    public static final String DEADLINE_VALIDATION_REGEX1 = "\\d{2}/\\d{2}/\\d{2}";
    public static final String DEADLINE_VALIDATION_REGEX2 = "[a-zA-Z]+";
    public static final String DEADLINE_VALIDATION_REGEX3 = "\\w+ \\d+";
    public static final String DEADLINE_VALIDATION_REGEX4 = "(\\d{2}/\\d{2}/\\d{2}) (\\d+)";
    public static final String DEADLINE_VALIDATION_REGEX5 = "\\d+";
    public static final String EMPTY_FIELD ="EMPTY_FIELD";

    public final String value;

    /**
     * Validates given deadline.
     *
     * @throws IllegalValueException if given deadline string is invalid.
     */
    public Deadline(String deadline) throws IllegalValueException {
        assert deadline != null;
        String trimmedDeadline = deadline.trim();
        if (!isValidDeadline(trimmedDeadline)) {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
        this.value = trimmedDeadline;
    }

    /**
     * Returns true if a given string is a valid Task due time.
     */
    public static boolean isValidDeadline(String test) {
        return test.matches(DEADLINE_VALIDATION_REGEX1) || test.matches(DEADLINE_VALIDATION_REGEX2) 
        		|| test.matches(DEADLINE_VALIDATION_REGEX3) || test.matches(DEADLINE_VALIDATION_REGEX4) || 
        		test.matches(DEADLINE_VALIDATION_REGEX5) || test.matches(EMPTY_FIELD);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.value.equals(((Deadline) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
