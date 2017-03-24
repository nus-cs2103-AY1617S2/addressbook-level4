package savvytodo.model.task;

import java.util.function.Predicate;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.commons.util.StringUtil;

//@@author A0140016B
/**
 * Represents a Task's priority in the task manager
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority implements Comparable<Priority> {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority should be 'low', 'medium' or 'high'";

    private static final int COMPARE_TO_SMALLER = -1;
    private static final int COMPARE_TO_EQUAL = 0;
    private static final int COMPARE_TO_GREATER = 1;

    /**
     * @author A0140016B
     * Get type enum object from it's name, ignoring cases
     * @param String recurrence type
     * @return Corresponding enum object
     */
    public enum Level {
        Low(1),
        Medium(2),
        High(3);

        private Integer value;

        Level(int value) {
            this.value = value;
        }

        int showInt() {
            return value;
        }
    }

    public final String value;
    private int level;

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

        if (priority.equalsIgnoreCase(Level.High.name())) {
            this.level = Level.High.ordinal();
        } else if (priority.equalsIgnoreCase(Level.Medium.name())) {
            this.level = Level.Medium.ordinal();
        } else {
            this.level = Level.Low.ordinal();
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

    //@@author A0124863A
    /**
     * Return predicate of the priority to filter tasks
     * @return predicate expression to help filter tasks
     */
    public Predicate<ReadOnlyTask> getPredicate() {
        return (ReadOnlyTask task) -> task.getPriority().equals(this);
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

    @Override
    public int compareTo(Priority o) {
        if (this.level > o.level) {
            return COMPARE_TO_GREATER;
        } else if (this.level < o.level) {
            return COMPARE_TO_SMALLER;
        } else {
            return COMPARE_TO_EQUAL;
        }
    }

}
//@@author A0140016B
