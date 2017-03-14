package seedu.todolist.model.task;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import java.util.Date;

import seedu.todolist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's start time in the to-do list.
 */

public class StartTime {

    public static final String MESSAGE_STARTTIME_CONSTRAINTS =
            "Start time should follow the format: DD-MM-YYYY TIME. E.g. \n"
            + "12-12-2008 3.30 PM";

    private Date startTime;

    public StartTime(String startTime) throws IllegalValueException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy h.mm a");
        Date temp = dateFormatter.parse(startTime, new ParsePosition(0));
        if (temp == null) {
            throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
        } else {
            this.startTime = temp;
        }
    }

    public String toString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy h.mm a");
        StringBuffer startTimeBuffer = dateFormatter.format(startTime, new StringBuffer(), new FieldPosition(0));
        return startTimeBuffer.toString();
    }

    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                && this.toString().equals(((StartTime) other).toString())); // state check
    }

    public int hashCode() {
        return this.toString().hashCode();
    }
}
