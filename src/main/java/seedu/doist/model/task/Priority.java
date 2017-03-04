package seedu.doist.model.task;

import seedu.doist.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's priority in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 * Default value is 1 if not set by user.
 */
public class Priority {

    public static final String EXAMPLE = "HIGH";
    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority should be 'NORMAL', "
            + "'IMPORTANT' or 'VERYIMPORTANT'";
    public static final PriorityLevel DEFAULT_PRIORITY = PriorityLevel.NORMAL;

    public enum PriorityLevel {
        NORMAL, IMPORTANT, VERYIMPORTANT
    }
    public final PriorityLevel priority;

    /**
     * If no parameters are given, it is set to default priority
     */
    public Priority() {
        this.priority = DEFAULT_PRIORITY;
    }

    /**
     * Validates given string priority.
     *
     * @throws IllegalValueException if given priority string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        priority = priority.toUpperCase();
        if (!isValidPriority(priority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.priority = PriorityLevel.valueOf(priority);
    }

    public PriorityLevel getPriorityLevel() {
        return priority;
    }

    /**
     * Returns true if a given string is a valid priority
     */
    public static boolean isValidPriority(String priority) {
        return priority.equals(PriorityLevel.VERYIMPORTANT.toString())
                || priority.equals(PriorityLevel.IMPORTANT.toString())
                || priority.equals(PriorityLevel.NORMAL.toString());
    }

    @Override
    public String toString() {
        return priority.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.priority.equals((((Priority) other).priority))); // state check
    }

    @Override
    public int hashCode() {
        return priority.toString().hashCode();
    }

}
