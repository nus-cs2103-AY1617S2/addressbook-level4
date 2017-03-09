package seedu.tasklist.model.task;

import seedu.tasklist.commons.exceptions.IllegalValueException;

public class Priority {
	public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Task priorities should only contain 'high', 'medium', or 'low', and it should not be blank";
	
	/*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String PRIORITY_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    
	public final String priority;
	
	/**
     * Validates given priority.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        assert priority != null;
        String trimmedPriority = priority.trim();
        if (!isValidPriority(trimmedPriority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.priority = trimmedPriority;
    }
    
    /**
     * Returns true if a given string is a valid Task name.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
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
}
