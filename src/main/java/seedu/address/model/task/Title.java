package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's title in the to-do list.
 * Guarantees: immutable; is valid as declared in {@link #isValidTitle(String)}
 */
public class Title {

    public static final String MESSAGE_TITLE_CONSTRAINTS =
            "Task titles should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the title must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TITLE_VALIDATION_REGEX = "^\\w+(.+)?";

    public final String title;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given title string is invalid.
     */
    public Title(String name) throws IllegalValueException {
        assert name != null;
        String trimmedTitle = name.trim();
        if (!isValidTitle(trimmedTitle)) {
            throw new IllegalValueException(MESSAGE_TITLE_CONSTRAINTS);
        }
        this.title = trimmedTitle;
    }

    /**
     * Returns true if a given string is a valid task title.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(TITLE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && this.title.equals(((Title) other).title)); // state check
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

}
