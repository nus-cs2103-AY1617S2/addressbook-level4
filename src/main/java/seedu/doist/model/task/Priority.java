package seedu.doist.model.task;

import seedu.doist.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's priority in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 * Default value is 1 if not set by user.
 */
public class Priority {

    public static final String EXAMPLE = "HIGH";
    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority should be 'LOW', 'MEDIUM' or 'HIGH'";
    public static final PriorityLevel DEFAULT_PRIORITY = PriorityLevel.LOW;

    public enum PriorityLevel {
        LOW, MEDIUM, HIGH
    }
    public final PriorityLevel priority;

    /**
     * If no parameters are given, it is set to default priority
     */
    public Priority() {
        this.priority = DEFAULT_PRIORITY;
    }
    
    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        if (!isValidPriority(priority)) {
            System.out.println(priority);
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.priority = PriorityLevel.valueOf(priority);
    }

    public PriorityLevel getPriorityLevel() {
        return priority;
    }

    /**
     * Returns true if a given integer is a valid priority string
     */
    public static boolean isValidPriority(String priority) {
        return priority.equals(PriorityLevel.HIGH.toString())
                || priority.equals(PriorityLevel.MEDIUM.toString())
                || priority.equals(PriorityLevel.LOW.toString());
    }

    @Override
    public String toString() {
        return priority.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.priority.toString().equals((((Priority) other).priority.toString()))); // state check
    }

    @Override
    public int hashCode() {
        return priority.toString().hashCode();
    }

}
