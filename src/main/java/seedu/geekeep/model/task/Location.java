package seedu.geekeep.model.task;

import seedu.geekeep.commons.exceptions.IllegalValueException;

//@@author A0121658E
/**
 * Represents a Task's location in GeeKeep. Guarantees: immutable; is valid as declared in
 * {@link #isValidLocation(String)}
 */
public class Location {

    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Task location can take any values, and it should not be blank";

    /*
     * The first character of the location must not be a whitespace, otherwise " " (a blank string) becomes a valid
     * input.
     */
    public static final String LOCATION_VALIDATION_REGEX = ".*";

    public final String value;

    /**
     * Returns true if a given string is a valid task location.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(LOCATION_VALIDATION_REGEX);
    }

    /**
     * Validates given location.
     *
     * @throws IllegalValueException
     *             if given location string is invalid.
     */
    public Location(String location) throws IllegalValueException {
        assert location != null;
        if (!isValidLocation(location)) {
            throw new IllegalValueException(MESSAGE_LOCATION_CONSTRAINTS);
        }
        this.value = location;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                        && this.value.equals(((Location) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }

}
