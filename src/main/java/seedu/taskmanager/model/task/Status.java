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

    private static final String MESSAGE_STATUS_DONE = "Completed";
    private static final String MESSAGE_STATUS_NOT_DONE = "Incomplete";

    public boolean value;

    /**
     * Default constructor
     */
    public Status() {
        value = false;
    }

    /**
     * For storage
     */
    public Status(boolean status) {
        this.value = status;
    }

    /**
     * For storage in String
     */
    public Status(String status) {
        assert status != null;
        if (status.equals(MESSAGE_STATUS_DONE)) {
            this.value = true;
        } else if (status.equals(MESSAGE_STATUS_NOT_DONE)) {
            this.value = false;
        } else {
            this.value = false;
            logger.warning("Invalid Status String, status default to NOT DONE");
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
