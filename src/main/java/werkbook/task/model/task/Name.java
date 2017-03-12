package werkbook.task.model.task;

import werkbook.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's name in the task book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Task names can contain any characters and spaces, and it should not be blank";

    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*.+";

    public final String taskName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Name(String name) throws IllegalValueException {
        assert name != null;
        String trimmedName = name.trim();
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.taskName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid task name.
     */
    public static boolean isValidName(String test) {
        test = test.trim();
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return taskName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.taskName.equals(((Name) other).taskName)); // state check
    }

    @Override
    public int hashCode() {
        return taskName.hashCode();
    }

}
