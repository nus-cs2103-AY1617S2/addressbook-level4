package seedu.address.model.task;
import seedu.address.commons.exceptions.IllegalValueException;

public class UrgencyLevel {

/**
 * Represents a Task's urgency level in the to-do list.
 * Guarantees: immutable; is valid as declared in {@link #isValidUrgencyLevel(String)}
 */

    public static final String MESSAGE_URGENCYLEVEL_CONSTRAINTS = "Task urgency levels should only contain numbers";
    public static final String URGENCYLEVEL_VALIDATION_REGEX = "^[0-5]*$";

    public final String value;

    /**
     * Validates given start time.
     *
     * @throws IllegalValueException if given start time string is invalid.
     */
    public UrgencyLevel(String starttime) throws IllegalValueException {
        assert starttime != null;
        String trimmedUrgencyLevel = starttime.trim();
        if (!isValidUrgencyLevel(trimmedUrgencyLevel)) {
            throw new IllegalValueException(MESSAGE_URGENCYLEVEL_CONSTRAINTS);
        }
        this.value = trimmedUrgencyLevel;
    }

    /**
     * Returns true if a given string is a valid task start time.
     */
    public static boolean isValidUrgencyLevel(String test) {
        return test.matches(URGENCYLEVEL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UrgencyLevel // instanceof handles nulls
                && this.value.equals(((UrgencyLevel) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
