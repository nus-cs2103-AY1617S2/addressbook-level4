package t15b1.taskcrusher.model.event;

import t15b1.taskcrusher.commons.exceptions.IllegalValueException;

/**
 * stores the location of an event. Location field is optional for an event.
 */
public class Location {

    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Location should only contain alphanumeric characters and spaces";

    /*
     * The first character of the location must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String LOCATION_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Validates given location string. This DOES NOT mean that the location must be identifiable on a map.
     *
     * @throws IllegalValueException if given location string is invalid.
     */
    public Location(String loc) throws IllegalValueException {
        assert loc != null;
        String trimmedloc = loc.trim();
        if (!isValidLocation(trimmedloc)) {
            throw new IllegalValueException(MESSAGE_LOCATION_CONSTRAINTS);
        }
        this.value = trimmedloc;
    }

    /**
     * Returns true if a given string is a valid location name.
     */
    public static boolean isValidLocation(String test) {
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
