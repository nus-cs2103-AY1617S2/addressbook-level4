package seedu.doist.model.task;

import seedu.doist.commons.exceptions.IllegalValueException;

//@@author A0140887W
/**
 * Represents a task's priority in the to-do list
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 * Default value is NORMAL if not set by user.
 */
public class Priority {

    public static final String EXAMPLE = "HIGH";
    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority should be 'NORMAL', "
            + "'IMPORTANT' or 'VERY IMPORTANT'";
    public static final PriorityLevel DEFAULT_PRIORITY = PriorityLevel.NORMAL;

    public enum PriorityLevel {
        NORMAL("Normal"), IMPORTANT("Important"), VERY_IMPORTANT("Very Important");

        private String strValue;
        PriorityLevel(String value) {
            this.strValue = value;
        }

        @Override
        public String toString() {
            return this.strValue;
        }
    }
    private final PriorityLevel priority;

    /**
     * If no parameters are given, it is set to default priority
     */
    public Priority() {
        this.priority = DEFAULT_PRIORITY;
    }

    /**
     * Validates given string priority.
     *
     * @throws IllegalValueException if given priority string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        final String processedPriority = processPriorityString(priority);
        if (!isValidPriority(processedPriority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.priority = PriorityLevel.valueOf(processedPriority);
    }

    public PriorityLevel getPriorityLevel() {
        return priority;
    }

    /**
     * Returns true if a given string is a valid priority
     */
    public static boolean isValidPriority(String priority) {
        return priority.equals(PriorityLevel.VERY_IMPORTANT.name())
                || priority.equals(PriorityLevel.IMPORTANT.name())
                || priority.equals(PriorityLevel.NORMAL.name());
    }

    /**
     * Process string to process all whitespace, spaces and new line and
     * change all characters to upper case so that it will be a
     * valid priority string
     * @returns string of the processed priority string
     */
    public static String processPriorityString(String priority) {
        // remove all trailing spaces, new line characters etc
        String processedPriority = priority.trim();

        // remove all leading spaces, new line characters etc
        processedPriority = processedPriority.replaceAll("^\\s+", "");

        // replace in-between spaces, new line characters etc with _
        processedPriority = processedPriority.replaceAll("\\s+", "_");
        processedPriority = processedPriority.toUpperCase();
        return processedPriority;
    }

    @Override
    public String toString() {
        return priority.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.priority.equals((((Priority) other).priority))); // state check
    }

    @Override
    public int hashCode() {
        return priority.toString().hashCode();
    }

}
