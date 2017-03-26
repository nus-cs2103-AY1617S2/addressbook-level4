package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents an Activity's TaskStatus in WhatsLeft.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class TaskStatus {
    
    //@@author A0121668A
    public static final String COMPLETED_TASK_STATUS = "[Done]";
    public static final String UNCOMPLETED_TASK_STATUS = "[Pending]";
    public static final boolean COMPLETED = true;
    public static final boolean UNCOMPLETED = false;
    
    public final String status;


    public TaskStatus(boolean status) throws IllegalValueException {
        if(status) {
            this.status = COMPLETED_TASK_STATUS;
        }
        else {
            this.status = UNCOMPLETED_TASK_STATUS;
        }
    }

    @Override
    public String toString() {
        return status;
    }
    
    //@@author
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskStatus // instanceof handles nulls
                && this.status.equals(((TaskStatus) other).status)); // state check
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }

}
