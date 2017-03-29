package org.teamstbf.yats.model.item;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class SimpleDate {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "Task deadline should only be in the format dd/mm/yyyy";
    public static final String DEADLINE_VALIDATION_REGEX = "(^[0-3][0-9]/[0-3][0-9]/(?:[0-9][0-9])?[0-9][0-9]$|^(?![\\s\\S]))";

    public final String value;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public SimpleDate(String deadline) throws IllegalValueException {
        assert deadline != null;
        String trimmedDeadline = deadline.trim();
        if (!isValidDeadline(trimmedDeadline)) {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
        this.value = trimmedDeadline;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidDeadline(String test) {
        return test.matches(DEADLINE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SimpleDate // instanceof handles nulls
                && this.value.equals(((SimpleDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
