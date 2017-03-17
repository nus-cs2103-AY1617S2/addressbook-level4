package savvytodo.model.task;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.commons.util.StringUtil;

/**
 * Represents a Task's priority in the task manager
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority should be 'low', 'medium' or 'high'";

    /**
     * @author A0140016B
     * Get type enum object from it's name, ignoring cases
     * @param String recurrence type
     * @return Corresponding enum object
     */
    public enum Level {
        Low,
        Medium,
        High
    }

    public final String value;

    /**
     * Validates given priority.
     *
     * @throws IllegalValueException if given priority string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        assert priority != null;
        String trimmedPriority = priority.trim();
        if (!isValidPriority(trimmedPriority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.value = StringUtil.firstCharUpperCaseRestLowerCase(trimmedPriority);
    }

    /**
     * Returns true if a given string is a valid task priority.
     */
    public static boolean isValidPriority(String test) {
        boolean matches = false;
        for (Level level : Level.values()) {
            if (level.toString().equalsIgnoreCase(test)) {
                matches = true;
            }
        }
        return matches;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                        && this.value.equalsIgnoreCase(((Priority) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
