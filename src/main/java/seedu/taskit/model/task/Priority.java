// @@author A0163996J

package seedu.taskit.model.task;

import seedu.taskit.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority in the task manager. Guarantees: immutable; is
 * valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {
    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Error saving priority, "
            + "valid inputs: 'high', 'medium', 'low'";


    enum PriorityEnum {HIGH, MEDIUM, LOW};
    private PriorityEnum priority;


    /**
     * Default constructor for Priority
     */
    public Priority() {
        this.priority = PriorityEnum.LOW;
    }
    /**
     * Validates given priority.
     *
     * @throws IllegalValueException
     *             if given priority string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        if (priority == null) {
            this.priority = PriorityEnum.LOW;
        }
        else {
            String trimmedPriority= priority.trim();

            PriorityEnum priorityValue = getPriorityValue(trimmedPriority.toLowerCase());
            if (priorityValue == null) {
                throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
            }
            this.priority = priorityValue;
        }
    }

    private PriorityEnum getPriorityValue(String priorityString) {
        if (priorityString.contains("high")) {
            return PriorityEnum.HIGH;
        }
        else if (priorityString.contains("medium")) {
            return PriorityEnum.MEDIUM;
        }
        else if (priorityString.contains("low") || priorityString == null) {
            return PriorityEnum.LOW;
        }
        return null;

    }

    @Override
    public String toString() {
        if (this.priority == PriorityEnum.HIGH) {
            return "high";
        }
        else if (this.priority == PriorityEnum.MEDIUM) {
            return "medium";
        }
        else {
            return "low";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                        && this.priority.equals(((Priority) other).priority)); // state
                                                                           // check
    }

    @Override
    public int hashCode() {
        return priority.hashCode();
    }
}
