package seedu.address.model.task;

import java.lang.Math;
import java.lang.NumberFormatException;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's ID in the TaskManager.
 * Guarantee: Immutable;
 */
public class IdentificationNumber {

    public static final ZERO = new IdentificationNumber(0);
    public static final ONE = new IdentificationNumber(1);

    public static final String MESSAGE_ID_CONSTRAINTS =
        "ID has to be a number";

    public static IdentificationNumber max(IdentificationNumber i1, IdentificationNumber i2) {
        return new IdentificationNumber(Math.max(i1.value, i2.value));
    }

    public final Integer value;

    /**
     * Value just needs to be not null
     */
    public IdentificationNumber(Integer value) {
        this.value = value;
    }

    public IdentificationNumber(String value) throws IllegalValueException {
        assert value != null;
        try {
            this.value = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(MESSAGE_ID_CONSTRAINTS);
        }
    }

    /** Return next index */
    public IdentificationNumber next() {
        return new IdentificationNumber(value + 1);
    }


    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IdentificationNumber // instanceof handles nulls
                && this.value.equals(((IdentificationNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
