//@@author A0141138N
package seedu.onetwodo.model.task;

import seedu.onetwodo.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority in the toDo list. Guarantees: immutable; is
 * valid as declared in {@link #isValidPriority(Char)}
 */
public class Priority {

    public static final String PRIORITY_CONSTRAINTS = "Priority can be high, medium or low";

    public String value;

    /**
     * Validates given priority.
     *
     * @throws IllegalValueException
     *             if given description string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        assert priority != null;
        priority = priority.trim();
        String upperPriority = priority.toUpperCase();
        char firstLetter = upperPriority.charAt(0);
        if (!isValidPriority(firstLetter)) {
            throw new IllegalValueException(PRIORITY_CONSTRAINTS);
        }
        /*
         * if (firstLetter == 'H') { this.value = "HIGH"; } else if (firstLetter
         * == 'M') { this.value = "MEDIUM"; } else { this.value = "LOW"; }
         */
        this.value = priority;
    }

    /**
     * Returns true if a given string is a valid task priority.
     */
    public static boolean isValidPriority(char test) {
        return ((test == 'H') || (test == 'M') || (test == 'L'));
    }

    public Priority getPriority() {
        return this;
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
                        && this.value.equals(((Priority) other).value)); // state
                                                                         // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
