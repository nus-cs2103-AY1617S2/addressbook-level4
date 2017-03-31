package savvytodo.model.task;

import savvytodo.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's location in the task manager. Guarantees: immutable; is
 * valid as declared in {@link #isValidLocation(String)}
 */
public class Location {

    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Task location can take any values, and it should not be blank";
    public static final String LOCATION_DEFAULT_VALUES = "Location has not been set.";

    /*
     * The first character of the location must not be a whitespace, otherwise
     * " " (a blank string) becomes a valid input.
     */
    public static final String LOCATION_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

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

    /**
     * Returns true if a given string is a valid task location.
     */
    public static boolean isValidLocation(String test) {
        String trimmedString = test.trim();
        return trimmedString.matches(LOCATION_VALIDATION_REGEX);
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
