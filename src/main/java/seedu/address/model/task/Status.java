package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's status in the task manager.
 * Guarantees: transaction between states are doable.
 */
public class Status {

    private static final String NOT_COMPLETED_MESSAGE = "Task is incomplete.";
    private static final String COMPLETED_MESSAGE = "Task is completed.";
    private static final String UNDEFINED_STATUS_MESSAGE = "An internal error has occured. Status not readable.";
    public final int status;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Status(int status) {
        this.status = status;
    }

    /**
     * Returns true if a given string is a valid task name.
     */
    public boolean isValidTransaction(int status) {
        if(this.status == status)
            return false;
        else
            return true;
    }


    @Override
    public String toString() {
        if (this.status == 0)
            return NOT_COMPLETED_MESSAGE;
        else if (this.status == 1)
            return COMPLETED_MESSAGE;
        return UNDEFINED_STATUS_MESSAGE;
    }

}
