package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's title in the task manager. Guarantees: immutable; is
 * valid as declared in {@link #isValidTitle(String)}
 */
public class Title {

    public static final String MESSAGE_TITLE_CONSTRAINTS = "Task names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the task must not be a whitespace, otherwise
     * " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String title;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException
     *             if given name string is invalid.
     */
    public Title(String title) throws IllegalValueException {
        assert title != null;
        String trimmedTitle= title.trim();

        // if (!isValidTitle(trimmedName)) {
        // throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        // }
        this.title = trimmedTitle;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                        && this.title.equals(((Title) other).title)); // state
                                                                           // check
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

}
