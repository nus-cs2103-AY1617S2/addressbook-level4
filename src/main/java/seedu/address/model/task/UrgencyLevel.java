package seedu.address.model.task;
import seedu.address.commons.exceptions.IllegalValueException;

public class UrgencyLevel {

    /**
     * Represents a Task's urgency level in the to-do list.
     * Guarantees: immutable; is valid as declared in {@link #isValidUrgencyLevel(String)}
     */

    public static final String MESSAGE_URGENCYLEVEL_CONSTRAINTS = "Task urgency levels should only contain numbers between 1 to 5";
    public static final String URGENCYLEVEL_VALIDATION_REGEX = "[1-5]";

    public final String value;
    public final int int_value;

    /**
     * Validates given urgency level.
     *
     * @throws IllegalValueException if given urgency level string is invalid.
     */
    public UrgencyLevel(String urgencyLevel) throws IllegalValueException {
        assert urgencyLevel != null;
        if(urgencyLevel.isEmpty()) {
            this.value = urgencyLevel;
            this.int_value = 0;
        } else {
            String trimmedUrgencyLevel = urgencyLevel.trim();
            if (!isValidUrgencyLevel(trimmedUrgencyLevel)) {
                throw new IllegalValueException(MESSAGE_URGENCYLEVEL_CONSTRAINTS);
            }
            this.value = trimmedUrgencyLevel;
            this.int_value = Integer.parseInt(value);
        }
    }

    /**
     * Returns true if a given string is a valid task urgency level.
     */
    public static boolean isValidUrgencyLevel(String test) {
        return test.matches(URGENCYLEVEL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    public int getIntValue(){
        return int_value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UrgencyLevel // instanceof handles nulls
                        && this.value.equals(((UrgencyLevel) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
