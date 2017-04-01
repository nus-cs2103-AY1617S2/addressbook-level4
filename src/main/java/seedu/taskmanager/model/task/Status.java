package seedu.taskmanager.model.task;

import seedu.taskmanager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's status in the task manager.
 */
public class Status {
    private static final String MESSAGE_STATUS_DONE = "Done";
    private static final String MESSAGE_STATUS_NOT_DONE = "Not Done";
    private static final String MESSAGE_STATUS_ERROR = "Invalid Status";
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
    
    public Status(String status) throws IllegalValueException {
        assert status != null;
        if (status == MESSAGE_STATUS_DONE) {
            this.value = true;
        } else if (status == MESSAGE_STATUS_NOT_DONE){
            this.value = false;
        } else {
            throw new IllegalValueException(MESSAGE_STATUS_ERROR);
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
