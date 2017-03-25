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
    	if (priority == null) {
            this.value = null;
            this.integerLevel = 0;
        } else if (priority == "high") {
        	this.value = priority;
        	this.integerLevel = 3;
        } else if (priority == "medium") {
        	this.value = priority;
        	this.integerLevel = 2;
        } else if (priority == "low") {
        	this.value = priority;
        	this.integerLevel = 1;
        } else {
        	throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
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
