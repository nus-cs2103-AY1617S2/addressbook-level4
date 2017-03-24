package todolist.model.task;


import todolist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's venue number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidVenue(String)}
 */
public class Venue {

    public static final String MESSAGE_VENUE_CONSTRAINTS =
            "Task venues should only be 1 alphanumeric strings";
    public static final String VENUE_VALIDATION_REGEX = ".+";

    public final String value;

    /**
     * Validates given value.
     *
     * @throws IllegalValueException if given value address string is invalid.
     */
    public Venue(String venue) throws IllegalValueException {
        assert venue != null;
        if (venue.isEmpty()) {
            this.value = venue;
        } else {
            String trimmedVenue = venue.trim();
            if (!isValidVenue(trimmedVenue)) {
                throw new IllegalValueException(MESSAGE_VENUE_CONSTRAINTS);
            }
            this.value = trimmedVenue;
        }
    }

    /**
     * Returns if a given string is a valid venue value.
     */
    public static boolean isValidVenue(String test) {
        return test.matches(VENUE_VALIDATION_REGEX);
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
