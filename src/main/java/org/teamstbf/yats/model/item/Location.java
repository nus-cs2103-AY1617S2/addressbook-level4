package org.teamstbf.yats.model.item;

import java.util.function.Predicate;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;

public class Location {

    private static final int LOCATION_MAXIMUM_LENGTH = 500;

    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Location can take any values, but can only be 400 characters in length. It can be left blank.";

    public final String value;
    
    private boolean hasLocation;

    /**
     * Validates given location.
     *
     * @throws IllegalValueException if given location string is invalid.
     */
    public Location(String location) throws IllegalValueException {
        assert location != null;
        String trimmedLocation = location.trim();
        if (!isValidLocation(trimmedLocation)) {
            throw new IllegalValueException(MESSAGE_LOCATION_CONSTRAINTS);
        }
        this.value = trimmedLocation;
        setLocationExist();
    }
    
    public void setLocationExist() {
    	this.hasLocation = true;
    }
    
    public void setLocationNonExist() {
    	this.hasLocation = false;
    }
    
    public boolean isLocationExist() {
    	return hasLocation;
    }
    
    public Predicate<Location> locationPredicate() {
    	return p -> p.isLocationExist();
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

    /**
     * Returns true if a given string is a valid Description. String should be blank("") and never null.
     */
    public static boolean isValidLocation(String string) {
        if (string == null) {
            return false;
        } else if (string.length() > LOCATION_MAXIMUM_LENGTH) {
            return false;
        } else {
            return true;
        }
    }
}
