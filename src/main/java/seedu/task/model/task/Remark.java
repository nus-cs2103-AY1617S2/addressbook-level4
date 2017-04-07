package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's phone number in KIT. Guarantees: immutable; is valid as
 * declared in {@link #isValidRemark(String)}
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS = "Task remarks can contain any character";
    public static final String REMARK_VALIDATION_REGEX = ".*";
    public final String value;

    //Allows an empty constructor
    public Remark() {
        this.value = "";
    }
    /**
     * Validates given remark.
     *
     * @throws IllegalValueException
     *             if given remark string is invalid.
     */
    public Remark(String remark) throws IllegalValueException {
        if (remark == null || remark.equals("")) {
            this.value = "";
        } else {
            String trimmedRemark = remark.trim();
            if (!isValidRemark(trimmedRemark)) {
                throw new IllegalValueException(MESSAGE_REMARK_CONSTRAINTS);
            }
            this.value = trimmedRemark;
        }
    }

    /**
     * Returns if a given string is a valid task remark.
     */
    public static boolean isValidRemark(String input) {
        return input.matches(REMARK_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                        && this.value.equals(((Remark) other).value)); // state
                                                                       // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
