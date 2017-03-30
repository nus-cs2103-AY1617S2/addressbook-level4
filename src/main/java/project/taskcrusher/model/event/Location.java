package project.taskcrusher.model.event;

import project.taskcrusher.commons.exceptions.IllegalValueException;

//@@author A0163962X
/**
 * stores the location of an event. Location field is optional for an event.
 */
public class Location {

    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Location should only contain alphanumeric characters and spaces";
    public static final String NO_LOCATION = "";
    public static final String LOCATION_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String location;

    /**
     * Creates a Location using the String passed
     *
     * @param deadline
     * @throws IllegalValueException if given location string is invalid.
     */
    public Location(String location) throws IllegalValueException {
        assert location != null;

        if (location.equals(NO_LOCATION)) {
            this.location = NO_LOCATION;
        } else if (isValidLocation(location)) {
            this.location = location;
        } else {
            throw new IllegalValueException(MESSAGE_LOCATION_CONSTRAINTS);
        }
    }

    @Override
    public String toString() {
        return location;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                && this.location.equals(((Location) other).location)); // state check
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }

    /**
     * Checks whether a location is valid
     *
     * @param location
     * @return true if description exists and contains at least one
     *         non-whitespace character
     */
    public static boolean isValidLocation(String location) {
        return location.matches(LOCATION_VALIDATION_REGEX);
    }

    public boolean hasLocation() {
        return !location.equals(NO_LOCATION);
    }
}
