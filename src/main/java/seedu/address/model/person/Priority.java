package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents an Activity's priority level in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Activity priority should only be high, medium or low";

    public final String value;

    /**
     * Validates given priority level.
     *
     * @throws IllegalValueException if given priority level is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        if (priority == null) {
            this.value = null;
        } else {
            String trimmedPriority = priority.trim();
            if (!isValidPriority(trimmedPriority)) {
                throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
            }
            this.value = trimmedPriority;
        }
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidPriority(String test) {
        if (test == null) {
            return true;
        }
        return PriorityLevel.isInEnum(test);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.value.equals(((Priority) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
