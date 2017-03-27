package seedu.todolist.model.task;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import java.util.Date;

import seedu.todolist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's start time in the to-do list.
 */

public class StartTime implements Comparable<StartTime> {

    public static final String MESSAGE_STARTTIME_CONSTRAINTS =
            "Start time should follow the format: DD-MM-YYYY (TIME - optional). E.g. \n"
            + "12-12-2008 3.30 PM, or simply 12-12-2008";

    private Date startTime;

    public StartTime(String startTime) throws IllegalValueException {
        Date temp = TimeUtil.parseTime(startTime);
        if (temp == null) {
            throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
        } else {
            this.startTime = temp;
        }
    }

    public StartTime(Date startTime) {
        this.startTime = startTime;
    }

    protected Date getStartTime() {
        return this.startTime;
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

    public int compareTo(StartTime other) {
        return startTime.compareTo(other.getStartTime());
    }

    public int hashCode() {
        return this.toString().hashCode();
    }
}
