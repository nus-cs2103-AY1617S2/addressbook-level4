package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's ID in the TaskManager.
 * Guarantee: Immutable;
 */
public class IdentificationNumber {

    public static final IdentificationNumber ZERO = new IdentificationNumber(0);
    public static final IdentificationNumber ONE = new IdentificationNumber(1);

    public static final String MESSAGE_ID_CONSTRAINTS =
        "ID has to be a number";

    public static IdentificationNumber max(IdentificationNumber i1, IdentificationNumber i2) {
        return new IdentificationNumber(Math.max(i1.value, i2.value));
    }

    public static final int DEFAULT_VALUE = 0;

    public Integer value;

    public IdentificationNumber() {
        this.value = DEFAULT_VALUE;
    }

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

    public IdentificationNumber(IdentificationNumber ID) {
        this.value = ID.value;
    }

    /** Increase value by 1 */
    public IdentificationNumber inc() {
        return new IdentificationNumber(++this.value);
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
