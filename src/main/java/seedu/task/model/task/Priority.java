//@@author A0164212U
package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority number in the address book.
 * A smaller priority number indicates a higher priority.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority implements Comparable<Priority> {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority should be between 1-3";
    public static final String PRIORITY_HIGH = "1";
    public static final String PRIORITY_MEDIUM = "2";
    public static final String PRIORITY_LOW = "3";
    public static final String PRIORITY_HIGH_COLOR = "red";
    public static final String PRIORITY_MEDIUM_COLOR = "darkorange";
    public static final String PRIORITY_LOW_COLOR = "yellow";

    public final String value;

    private String priorityColor;

    /**
     * Validates given priority number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        if (priority != null) {
            String trimmedPriority = priority.trim();
            if (!isValidPriority(trimmedPriority)) {
                throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
            }
            this.value = trimmedPriority;
        } else {
            this.value = PRIORITY_LOW;
        }

        setPriorityColor(this.value);
    }

    /**
     * Returns true if a given string is a valid task priority.
     */
    public static boolean isValidPriority(String test) {
        return (test.equals(PRIORITY_HIGH) || test.equals(PRIORITY_MEDIUM) || test.equals(PRIORITY_LOW));
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

    public String getPriorityColor() {
        return priorityColor;
    }

    public void setPriorityColor(String priorityValue) {
        switch(priorityValue) {
        case PRIORITY_HIGH:
            priorityColor = PRIORITY_HIGH_COLOR;
            break;
        case PRIORITY_MEDIUM:
            priorityColor = PRIORITY_MEDIUM_COLOR;
            break;
        case PRIORITY_LOW:
            priorityColor = PRIORITY_LOW_COLOR;
            break;
        default:
            priorityColor = PRIORITY_LOW_COLOR;
            break;
        }
    }

    //@@author A0163559U
    /**
     * Results in Priority sorted in ascending order.
     */
    @Override
    public int compareTo(Priority comparePriority) {
        int thisValue = Integer.parseInt(this.value);
        int otherValue = Integer.parseInt(comparePriority.value);
        return thisValue - otherValue;
    }
    //@@author

}
