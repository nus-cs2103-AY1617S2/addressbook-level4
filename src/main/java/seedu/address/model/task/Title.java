package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's name in the address book. Guarantees: immutable; is
 * valid as declared in {@link #isValidName(String)}
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

         //if (!isValidName(trimmedTitle)) {
         //throw new IllegalValueException(MESSAGE_TITLE_CONSTRAINTS);
         //}
        this.title = trimmedTitle;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
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
