package seedu.taskmanager.model.task;

import seedu.taskmanager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's title in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidTitle(String)}
 */
public class Title {

    public static final String MESSAGE_TITLE_CONSTRAINTS =
            "Task titles should only contain alphanumeric characters, spaces, and _,.?':;&%$@()!- symbols\n" +
                    "It should not be blank";

    // @@author A0114269E
    /*
     * The first character of the title must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * Allows some commonly used special characters "_,.?':;&%$@()!-"
     */
//    public static final String TITLE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String TITLE_VALIDATION_REGEX = "[0-9a-zA-Z_,.?':;&%$@()!-][ 0-9a-zA-Z_,.?':;&%$@()!-]*";
    // @@author

    public final String value;

    /**
     * Validates given title.
     *
     * @throws IllegalValueException if given title string is invalid.
     */
    public Title(String title) throws IllegalValueException {
        assert title != null;
        String trimmedTitle = title.trim();
        if (!isValidTitle(trimmedTitle)) {
            throw new IllegalValueException(MESSAGE_TITLE_CONSTRAINTS);
        }
        this.value = trimmedTitle;
    }

    /**
     * Returns true if a given string is a valid title.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(TITLE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && this.value.equals(((Title) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
