package project.taskcrusher.model.shared;

import project.taskcrusher.commons.exceptions.IllegalValueException;

//@@author A0163962X
/**
 * Represents a Task's priority. Can take on values from 1 to 3
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority implements Comparable<Priority> {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Priority should only take the value from 1 to 3 when specified. Its default is 0";
    public static final String NO_PRIORITY = "0";
    public static final String PRIORITY_VALIDATION_REGEX = "[1-3]";

    public final String priority;

    /**
     * Validates given priority value.
     *
     * @throws IllegalValueException if given priority value is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        assert priority != null;

        if (priority.equals(NO_PRIORITY)) {
            this.priority = NO_PRIORITY;
        } else if (isValidPriority(priority)) {
            this.priority = priority;
        } else {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
    }

    @Override
    public String toString() {
        return priority;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                        && this.priority.equals(((Priority) other).priority)); // state check
    }

    @Override
    public int hashCode() {
        return priority.hashCode();
    }

    /**
     * Checks whether a priority is valid
     * @param priority
     * @return
     */
    public static boolean isValidPriority(String priority) {
        return priority.matches(PRIORITY_VALIDATION_REGEX);
    }

    public boolean hasPriority() {
        return !priority.equals(NO_PRIORITY);
    }

    @Override
    public int compareTo(Priority another) {
        int thisPriority = Integer.parseInt(this.priority);
        int anotherPriority = Integer.parseInt(another.priority);
        return anotherPriority - thisPriority;
    }

}
