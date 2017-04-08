package seedu.whatsleft.model.activity;

import seedu.whatsleft.commons.exceptions.IllegalValueException;

//@@author A0148038A
/**
 * Represents an activity's location in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location {

    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Activity location can take any values.\n";

    /*
     * The first character of the location must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String LOCATION_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given location.
     *
     * @param a location in string format to check
     * @throws IllegalValueException if given location string is invalid.
     */
    public Location(String location) throws IllegalValueException {
        if (location == null) {
            this.value = null;
        } else {
            if (!isValidLocation(location)) {
                throw new IllegalValueException(MESSAGE_LOCATION_CONSTRAINTS);
            }
            this.value = location;
        }
    }

    /**
     * Returns true if a given string is a valid activity location.
     */
    public static boolean isValidLocation(String test) {
        if (test == null) {
            return true;
        }
        if (test == "") {
            return false;
        }
        return test.matches(LOCATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
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

}
