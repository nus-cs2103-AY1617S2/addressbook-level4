//@@author A0144885R
package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.date.DateFactory;
import seedu.address.model.task.date.TaskDate;

/**
 * Represents a Task's deadline in the TaskManager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
        "Deadline accepts following formats: 30-10-2012 10:10 am; Sun, March 14 2017 23:59, ...\n"
        + "Please make sure valid existing date is used";


    public final TaskDate date;

    public Deadline() {
        DateFactory dateFactory = new DateFactory();
        date = dateFactory.getUnassignedTime();
    }

    /**
     * Constructor for Deadline.
     */
    public Deadline(String dateString) throws IllegalValueException {
        assert dateString != null;

        DateFactory dateFactory = new DateFactory();
        try {
            date = dateFactory.getTaskDateFromString(dateString);
        } catch (IllegalValueException e) {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
    }

    public static boolean isValidDeadline(String dateString) {
        DateFactory dateFactory = new DateFactory();
        try {
            TaskDate date = dateFactory.getTaskDateFromString(dateString);
            return true;
        } catch (IllegalValueException e) {
            return false;
        }
    }

    public boolean isFloating() {
        return date.isFloating();
    }

    public boolean isOverdue() {
        return date.hasPassed();
    }

    public boolean isToday() {
        return date.isHappeningToday();
    }

    public boolean isTomorrow() {
        return date.isHappeningTomorrow();
    }

    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Deadline // instanceof handles nulls
                    && this.date.equals(((Deadline) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
