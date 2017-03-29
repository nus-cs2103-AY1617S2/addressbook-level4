package seedu.opus.model.task;

import seedu.opus.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's status in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    //@@author A0124368A
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
    //@@author

    public static final String MESSAGE_STATUS_CONSTRAINTS = "Status can only be 'complete' or 'incomplete";
    //@@author A0124368A
    public static final String STATUS_COMPLETE = "complete";
    public static final String STATUS_INCOMPLETE = "incomplete";

    public final Flag value;
    //@@author

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

    //@@author A0124368A
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

    /**
     * @return the current value of status
     */
    public Flag getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
    //@@author

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
