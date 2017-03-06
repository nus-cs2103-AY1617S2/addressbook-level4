package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's venue in task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidVenue(String)}
 */
public class Venue {

    public static final String MESSAGE_VENUE_CONSTRAINTS =
            "task venue can be in any form.";
    //TODO: public static final String DATE_VALIDATION_REGEX = 

    public final String value;

    /**
     * Validity given venue.
     *
     * @throws IllegalValueException if given venue string is invalid.
     */
    public Venue(String venue) throws IllegalValueException {
        assert venue != null;
        String trimmedVenue = venue.trim();
        if (!isValidVenue(trimmedVenue)) {
            throw new IllegalValueException(MESSAGE_VENUE_CONSTRAINTS);
        }
        this.value = trimmedVenue;
    }

    /**
     * Returns if a given string is a valid venue.
     */
    public static boolean isValidVenue(String test) {
        return true;
        //TODO: add REGEX
        //return test.matches(EMAIL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Venue // instanceof handles nulls
                && this.value.equals(((Venue) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
