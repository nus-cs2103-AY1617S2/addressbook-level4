package seedu.geekeep.model.task;

import seedu.geekeep.commons.exceptions.IllegalValueException;

//@@author A0121658E
/**
 * Represents a Task's title in the Task Manager. Guarantees: immutable; is valid as declared in
 * {@link #isValidTitle(String)}
 */
public class Title {

    public static final String MESSAGE_TITLE_CONSTRAINTS =
            "Task titles should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the title must not be a whitespace, otherwise " " (a blank string) becomes a valid
     * input.
     */
    public static final String TITLE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String title;

    /**
     * Returns true if a given string is a valid task title.
     */
    public static boolean isValidTitle(String testTitle) {
        return testTitle.matches(TITLE_VALIDATION_REGEX);
    }

    /**
     * Validates given title.
     *
     * @throws IllegalValueException
     *             if given title string is invalid.
     */
    public Title(String title) throws IllegalValueException {
        assert title != null;
        String trimmedTitle = title.trim();
        if (!isValidTitle(trimmedTitle)) {
            throw new IllegalValueException(MESSAGE_TITLE_CONSTRAINTS);
        }
        this.title = trimmedTitle;
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

    @Override
    public String toString() {
        return title;
    }

}
