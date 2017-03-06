package seedu.address.model.task;

import java.lang.Math;
import java.lang.NumberFormatException;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's index in the TaskManager.
 * Guarantee: Immutable;
 */
public class Index {

    public static final String MESSAGE_INDEX_CONSTRAINTS =
        "Index has to be a number";

    public static Index max(Index i1, Index i2) {
        return new Index(Math.max(i1.value, i2.value));
    }

    public final Integer value;

    /**
     * Index just needs to be not null
     */
    public Index(Integer value) {
        this.value = value;
    }

    public Index(String value) throws IllegalValueException {
        assert value != null;
        try {
            this.value = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(MESSAGE_INDEX_CONSTRAINTS);
        }
    }

    /** Return next index */
    public Index next() {
        return new Index(value + 1);
    }


    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Index // instanceof handles nulls
                && this.value.equals(((Index) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
