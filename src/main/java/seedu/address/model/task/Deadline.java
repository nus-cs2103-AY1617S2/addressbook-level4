package seedu.address.model.task;

import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;

public class Deadline {

    /*
     * Represents a Task's deadline in the task manager.
     * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
     */

    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
            "Task deadline should be after the current time";
    private static final int LATER_THAN_CURRENT_DATE = 1;

    public final Date deadline;

    /**
     * Validates given deadline.
     *
     * @throws IllegalValueException if given deadline is invalid.
     */
    public Deadline(Date deadline) throws IllegalValueException {
        assert deadline != null;
        if (!isValidDeadline(deadline)) {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
        this.deadline = deadline;
    }

    /**
     * Returns true if a given date is a valid task deadline.
     */
    public static boolean isValidDeadline(Date test) {
        int result = test.compareTo(new Date());
        if (result == LATER_THAN_CURRENT_DATE) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return deadline.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.deadline.equals(((Name) other).fullName)); // state check
        
    }

    @Override
    public int hashCode() {
        return deadline.hashCode();
    }
}
