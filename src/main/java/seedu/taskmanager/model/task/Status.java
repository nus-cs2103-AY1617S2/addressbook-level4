package seedu.taskmanager.model.task;

import java.util.logging.Logger;

import seedu.taskmanager.commons.core.LogsCenter;


// @@author A0114269E
/**
 * Represents a Task's status in the task manager.
 * @param true for task that is done
 * @param false for task yet to be done
 */
public class Status {
    private static final Logger logger = LogsCenter.getLogger(Status.class);

    private static final String MESSAGE_STATUS_DONE = "Done";
    private static final String MESSAGE_STATUS_NOT_DONE = "Incomplete";

    public boolean value;

    /**
     * Default constructor
     */
    public Status() {
        value = false;
    }

    /**
     * For copy operation
     */
    public Status(boolean status) {
        this.value = status;
    }

    /**
     * To recreate model from storage in String
     */
    public Status(String status) {
        assert status != null;
        this.value = Status.toBoolean(status);
    }

    public static boolean toBoolean(String status) {
        if (status.equals(MESSAGE_STATUS_DONE)) {
             return true;
        } else if (status.equals(MESSAGE_STATUS_NOT_DONE)) {
            return false;
        } else {
            logger.warning("Unknown Status String, status default to Incomplete");
            return false;
        }
    }

    @Override
    public String toString() {
        if (value) {
            return MESSAGE_STATUS_DONE;
        } else {
            return MESSAGE_STATUS_NOT_DONE;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                        && this.toString().equals(((Status) other).toString())); // state
        // check
    }
}
//@@author
