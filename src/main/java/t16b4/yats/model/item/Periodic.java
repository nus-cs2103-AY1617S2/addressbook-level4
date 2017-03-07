package t16b4.yats.model.item;

import t16b4.yats.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's Periodicity in the Task Manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidPeriod(String)}
 */

public class Periodic {

    public static final String MESSAGE_PERIODIC_CONSTRAINTS =
            "Periodicity must be daily|weekly|monthly";
    public static final String PERIODIC_VALIDATION_REGEX = ".*(none|daily|monthly|weekly).*";

    public final String value;

    /**
     * Validates given period.
     *
     * @throws IllegalValueException if given period string is invalid.
     */
    public Periodic(String period) throws IllegalValueException {
        assert period != null;
        String trimmedPeriod = period.trim();
        if (!isValidPeriod(trimmedPeriod)) {
            throw new IllegalValueException(MESSAGE_PERIODIC_CONSTRAINTS);
        }
        this.value = trimmedPeriod;
    }

    /**
     * Returns if a given string is a valid period.
     */
    public static boolean isValidPeriod(String test) {
        return test.matches(PERIODIC_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Periodic // instanceof handles nulls
                && this.value.equals(((Periodic) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
