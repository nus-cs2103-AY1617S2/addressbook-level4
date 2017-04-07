package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's location in KIT. Guarantees: immutable; is valid as
 * declared in {@link #isValidLocation(String)}
 */
public class Location {

    public static final String MESSAGE_LOCATION_CONSTRAINTS = "Task locations can take any values";

    /*
     * The first character of the location must not be a whitespace, otherwise
     * " " (a blank string) becomes a valid input.
     */
    public static final String LOCATION_VALIDATION_REGEX = "[^\\s].*";
    public final String value;

    //Allows an empty constructor
    public Location() {
        this.value = "";
    }
    /**
     * Validates given location.
     *
     * @throws IllegalValueException
     *             if given location string is invalid.
     */
    public Location(String location) throws IllegalValueException {
        if (location == null || location.trim().equals("")) {
            this.value = "";
        } else {
            this.value = location.trim();
//            if (!isValidLocation(location)) {
//                throw new IllegalValueException(MESSAGE_LOCATION_CONSTRAINTS);
//            }
//            this.value = trimmedLocation;
        }
    }

    /**
     * Returns true if a given string is a valid task email.
     */
    public static boolean isValidLocation(String input) {
        return input.matches(LOCATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                        && this.value.equals(((Location) other).value)); // state
                                                                         // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
