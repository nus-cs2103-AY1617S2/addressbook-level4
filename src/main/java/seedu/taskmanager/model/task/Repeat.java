package seedu.taskmanager.model.task;

import seedu.taskmanager.commons.exceptions.IllegalValueException;

// @@author A0140032E
/**
 * Represents a Task's repeat pattern in the task manager. Guarantees:
 * immutable; is valid as declared in {@link #isValidRepeat(String)}
 */
public class Repeat {

    public static final String MESSAGE_REPEAT_CONSTRAINTS = "Repeat patterns should be either "
            + "DAY, WEEK, MONTH or YEAR";

    public enum RepeatPattern {
        DAY, WEEK, MONTH, YEAR
    }

    public final RepeatPattern pattern;

    /**
     * Validates given repeat pattern.
     *
     * @throws IllegalValueException
     *             if given repeat pattern string is invalid.
     */
    public Repeat(String repeat) throws IllegalValueException {
        assert repeat != null;
        String trimmedAndUpperCaseRepeat = repeat.trim().toUpperCase();
        this.pattern = toEnum(trimmedAndUpperCaseRepeat);
    }

    /**
     * Returns RepeatPattern enum if a given string is a valid repeat pattern.
     */
    private RepeatPattern toEnum(String trimmedRepeat) throws IllegalValueException {
        switch (trimmedRepeat) {
        case "DAY":
            return RepeatPattern.DAY;
        case "WEEK":
            return RepeatPattern.WEEK;
        case "MONTH":
            return RepeatPattern.MONTH;
        case "YEAR":
            return RepeatPattern.YEAR;
        default:
            throw new IllegalValueException(MESSAGE_REPEAT_CONSTRAINTS);
        }
    }

    @Override
    public String toString() {
        return pattern.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Repeat // instanceof handles nulls
                        && this.pattern.equals(((Repeat) other).pattern)); // state
        // check
    }

    @Override
    public int hashCode() {
        return pattern.hashCode();
    }
}
// @@author
