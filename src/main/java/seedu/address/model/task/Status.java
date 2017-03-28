//@@author A0144885R
package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's status in the TaskManager.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 * Task status can be one of the following values:
 *      + Done
 *      + Floating
 *      + Overdue
 *      + Today
 *      + Tomorrow
 *      + This Week
 *      + future
 *  ** Status should not be used when comparing tasks as it is volatile and changes
 *  depending on current time **
 */
public class Status {

    public static final String MESSAGE_STATUS_CONSTRAINTS =
        "User can only set task status as Undone or Done";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String[] ALLOWED_STATUS_VALUES = {
        "Done", "Undone", "Floating", "Overdue",
        "Today", "Tomorrow", "Future"
    };

    /* Status values string contants */
    public static final String DONE = "Done";
    public static final String UNDONE = "Undone";
    public static final String FLOATING = "Floating";
    public static final String OVERDUE = "Overdue";
    public static final String TODAY = "Today";
    public static final String TOMORROW = "Tomorrow";
    public static final String FUTURE = "Future";

    public final String status;

    public Status() {
        status = UNDONE;
    }

    /**
     * Validates given status.
     *
     * @throws IllegalValueException if given status string is invalid.
     */
    public Status(String status) throws IllegalValueException {
        assert status != null;
        for (String statusString : ALLOWED_STATUS_VALUES) {
            // Compare insensitively
            if (statusString.toLowerCase().equals(status.toLowerCase())) {
                this.status = status;
                return;
            }
        }
        throw new IllegalValueException(MESSAGE_STATUS_CONSTRAINTS);
    }

    /**
     * Returns true if a given string is a valid task status.
     */
    public static boolean isValidStatus(String status) {
        for (String statusString : ALLOWED_STATUS_VALUES) {
            // Compare insensitively
            if (statusString.toLowerCase().equals(status.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && this.status.equals(((Status) other).status)); // state check
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }

}
