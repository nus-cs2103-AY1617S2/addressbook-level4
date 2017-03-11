package seedu.address.model.task.date;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Time Value that is unassigned by user.
 */
public class TimeUnassigned implements TaskDate {

    public final String MESSAGE_TIMEUNASSIGNED_CONSTRAINTS =
        "Leave deadline field empty for task with time unassigned";

    public final String MESSAGE_UNASSIGNED_DATE = "Unassigned";

    public TimeUnassigned() {}

    public TimeUnassigned(String dateString) throws IllegalValueException {
        if (dateString != MESSAGE_UNASSIGNED_DATE) {
            throw new IllegalValueException(MESSAGE_TIMEUNASSIGNED_CONSTRAINTS);
        }
    }

    @Override
    public String toString() {
        return MESSAGE_UNASSIGNED_DATE;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TimeUnassigned); // instanceof handles nulls
    }

    @Override
    public int hashCode() {
        return MESSAGE_UNASSIGNED_DATE.hashCode();
    }
}
