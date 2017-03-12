package t15b1.taskcrusher.model.task;

import t15b1.taskcrusher.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority. Can take on values from 1 to 3
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = 
            "Priority should only take the value from 1 to 3 when specified. It's default is 0";
    public static final String PRIORITY_VALIDATION_REGEX = "[0-3]";
    public static final String PRIORITY_DEFAULT_VALUE = "0";

    public final String value;

    /**
     * Validates given priority value.
     *
     * @throws IllegalValueException if given priority value is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        assert priority != null;
        String trimmedPriority = priority.trim();
        if (!isValidPriority(trimmedPriority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.value = trimmedPriority;
    }
    
    public boolean isDefaultPriority(){
        return value.equals(PRIORITY_DEFAULT_VALUE);
    }
    
    /**
     * Returns true if a given string is a valid priority value
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
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
