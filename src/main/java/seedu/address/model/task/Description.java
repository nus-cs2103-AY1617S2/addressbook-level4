package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Task Descriptions should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DECRIPTION_VALIDATION_REGEX = "*";

    public final String fullDescription;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Description(String description) throws IllegalValueException {
        assert description != null;
        String trimmedName = description.trim();
        if (!isValidDescription(trimmedName)) {
            throw new IllegalValueException(MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        this.fullDescription = trimmedName;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidDescription(String test) {
        //return test.matches(DECRIPTION_VALIDATION_REGEX);
        return true;
    }


    @Override
    public String toString() {
        return fullDescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.fullDescription.equals(((Description) other).fullDescription)); // state check
    }

    @Override
    public int hashCode() {
        return fullDescription.hashCode();
    }

}
