package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author A0148038A
/**
 * Represents an Activity's priority level in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority should only be high, medium or low";

    public final String value;
    public final int integerLevel;

    /**
     * Validates given priority level.
     *
     * @throws IllegalValueException if given priority level is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        priority = priority.toLowerCase();
        if (priority.equals("high") || priority.equals("h")) {
            this.value = "high";
            this.integerLevel = 1;
        } else if (priority.equals("medium") || priority.equals("m")) {
            this.value = "medium";
            this.integerLevel = 2;
        } else if (priority.equals("low") || priority.equals("l")) {
            this.value = "low";
            this.integerLevel = 3;
        } else {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
    }

    public static boolean isValidPriority(String arg) {
        if (arg.equals("high") || arg.equals("medium") || arg.equals("low")) {
            return true;
        } else {
            return false;
        }
    }

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

    public int compareTo(Priority o) {
        return this.integerLevel - o.integerLevel;
    }

}
