package typetask.model.task;

import typetask.commons.exceptions.IllegalValueException;

//@@author A0144902L
/**
* Represents a Task's priority in TypeTask.
* Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
*/
public class Priority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority level should only contain"
            + " the words Yes, No, High, Low (case-insensitive) or the letters y(Y), n(N), H(h), L(l)";

    public static final String HIGH_PRIORITY = "High";
    public static final String LOW_PRIORITY = "Low";
    public static final String PRIORITY_REGEX = "^(?:Yes|No|yes|YES|NO|no|y|n|Y|N|High|Low|h|l)$";
    public final String value;

    /**
     * Validates given priority.
     *
     * @throws IllegalValueException if given priority level string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        assert priority != null;

        String trimmedPriority = priority.trim();

        if ("".equals(priority)) {
            this.value = LOW_PRIORITY;
        } else {
            if (!isValidPriority(trimmedPriority)) {
                throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
            }
            if (trimmedPriority.contains("H") || trimmedPriority.contains("h") ||
                    trimmedPriority.contains("y") || trimmedPriority.contains("Y")) {
                this.value = HIGH_PRIORITY;
            } else {
                this.value = LOW_PRIORITY;
            }
        }
    }

    /**
     * Returns true if a given string is a valid task priority level.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_REGEX) ||
                "".equals(test);
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
        return this.hashCode();
    }
}
