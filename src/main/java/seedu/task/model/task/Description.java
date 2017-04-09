package seedu.task.model.task;

import java.util.Comparator;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's description in the Task Manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description implements Comparable<Description> {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Task descriptions cannot start with a space, "
                    + "should only contain alphanumeric characters and spaces, "
                    + "and it should not be blank";

    /*
     * The first character of the task must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DESCRIPTION_VALIDATION_REGEX = "^[^ ][A-Za-z0-9!@#$%^&*, ]++$";

    public final String description;

    /**
     * Validates given description.
     *
     * @throws IllegalValueException if given description string is invalid.
     */
    public Description(String description) throws IllegalValueException {
        assert description != null;
        String trimmedDescription = description.trim();
        if (!isValidDescription(trimmedDescription)) {
            throw new IllegalValueException(MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        this.description = trimmedDescription;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                        && this.description.equals(((Description) other).description)); // state check
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    @Override
    public int compareTo(Description other) {
        return this.description.compareTo(other.description);
    }

    public static Comparator<Description> descriptionComparator = new Comparator<Description>() {

        @Override
        public int compare(Description description1, Description description2) {
            return description1.compareTo(description2);
        }

    };

}
