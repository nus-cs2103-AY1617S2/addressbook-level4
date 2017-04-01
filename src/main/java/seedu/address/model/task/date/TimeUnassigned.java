//@@author A0144885R
package seedu.address.model.task.date;

/**
 * Represents a Time Value that is unassigned by user.
 */
public class TimeUnassigned implements TaskDate {

    public final String MESSAGE_TIMEUNASSIGNED_CONSTRAINTS =
        "Leave deadline field empty for task with time unassigned";

    public final String MESSAGE_UNASSIGNED_DATE = "Unassigned";

    public TimeUnassigned() {}

    /**
     * For task with unassigned time, only isfloating is true
     */
    public boolean isFloating() {
        return true;
    }

    public boolean hasPassed() {
        return false;
    }

    public boolean isHappeningToday() {
        return false;
    }

    public boolean isHappeningTomorrow() {
        return false;
    }

    public DateValue getBeginning() {
        return null;
    }

    public DateValue getEnding() {
        return null;
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
