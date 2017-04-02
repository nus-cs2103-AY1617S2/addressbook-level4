//@@author A0141138N
package seedu.onetwodo.model.task;

import seedu.onetwodo.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority in the toDo list. Guarantees: immutable; is
 * valid as declared in {@link #isValidPriority(Char)}
 */
public class Priority implements Comparable<Priority> {
    public static final String PRIORITY_CONSTRAINTS = "Priority can only be high, medium or low";
    public static final String HIGH_LABEL = "HIGH";
    public static final String MEDIUM_LABEL = "MEDIUM";
    public static final String LOW_LABEL = "LOW";
    private static final char HIGH_CHAR = 'H';
    private static final char MEDIUM_CHAR = 'M';
    private static final char LOW_CHAR = 'L';

    public String value;

    /**
     * Validates given priority.
     *
     * @throws IllegalValueException
     *             if given description string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        assert priority != null;
        String upperPriority = priority.trim().toUpperCase();
        if (!isValidPriority(upperPriority)) {
            throw new IllegalValueException(PRIORITY_CONSTRAINTS);
        } else {
            setPriority(upperPriority);
        }
    }

    /**
     * Returns true if a given string is a valid task priority.
     */
    public static boolean isValidPriority(String userInput) {
        return userInput.isEmpty() || userInput.charAt(0) == HIGH_CHAR || userInput.charAt(0) == MEDIUM_CHAR
                || userInput.charAt(0) == LOW_CHAR;
    }

    /**
     * Sets the priority
     *
     * @param upperPriority
     */
    private void setPriority(String upperPriority) {
        char firstLetter = upperPriority.charAt(0);
        switch (firstLetter) {
        case HIGH_CHAR:
            this.value = HIGH_LABEL;
            break;
        case MEDIUM_CHAR:
            this.value = MEDIUM_LABEL;
            break;
        case LOW_CHAR:
            this.value = LOW_LABEL;
            break;
        default:
        }
    }

    public boolean hasPriority() {
        return !value.trim().isEmpty();
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

    @Override
    public int compareTo(Priority p) {
        if (!hasPriority()) {
            return -1;
        }
        if (!p.hasPriority()) {
            return 1;
        }
        if (p.value.equals(value)) {
            return 0;
        }
        switch (value) {
        case HIGH_LABEL:
            return 1;
        case MEDIUM_LABEL:
            return p.value.equals(LOW_LABEL) ? 1 : -1;
        case LOW_LABEL:
            return -1;
        default:
            return -1;
        }
    }
}
