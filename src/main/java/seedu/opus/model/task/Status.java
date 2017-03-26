package seedu.opus.model.task;

import seedu.opus.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's status in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static enum Flag {

        COMPLETE,
        INCOMPLETE;

        @Override
        public String toString() {
            switch(this) {
            case COMPLETE:
                return STATUS_COMPLETE;
            case INCOMPLETE:
                return STATUS_INCOMPLETE;
            default:
                throw new AssertionError();
            }
        }

    }

    public static final String MESSAGE_STATUS_CONSTRAINTS = "Status can only be 'complete' or 'incomplete";
    public static final String STATUS_COMPLETE = "complete";
    public static final String STATUS_INCOMPLETE = "incomplete";

    public final Flag value;

    /**
     * Validates given status.
     *
     * @throws IllegalValueException if given status is invalid.
     */
    public Status(String status) throws IllegalValueException {
        assert status != null;
        String trimmedStatus = status.trim().toLowerCase();
        if (!isValidStatus(trimmedStatus)) {
            throw new IllegalValueException(MESSAGE_STATUS_CONSTRAINTS);
        }
        this.value = Flag.valueOf(trimmedStatus.toUpperCase());
    }

    /**
     * Initialise a Status with value of incomplete
     */
    public Status() {
        this.value = Flag.INCOMPLETE;
    }

    /**
     * Returns if a given string is a valid task status.
     */
    public static boolean isValidStatus(String test) {
        return test.equals(STATUS_COMPLETE) || test.equals(STATUS_INCOMPLETE);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && this.value.equals(((Status) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
