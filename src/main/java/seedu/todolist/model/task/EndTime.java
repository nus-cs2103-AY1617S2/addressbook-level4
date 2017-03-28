package seedu.todolist.model.task;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import java.util.Date;

import seedu.todolist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's end time in the to-do list.
 */

public class EndTime implements Comparable<EndTime> {

    public static final String MESSAGE_ENDTIME_CONSTRAINTS =
            "End time should follow the format: DD-MM-YYYY TIME. E.g. \n"
            + "12-12-2008 3.30 PM";

    private Date endTime;

    public EndTime(String endTime) throws IllegalValueException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy h.mm a");
        Date temp = dateFormatter.parse(endTime, new ParsePosition(0));
        if (temp == null) {
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        } else {
            this.endTime = temp;
        }
    }

    public EndTime(Date endTime) {
        this.endTime = endTime;
    }

    protected Date getEndTime() {
        return this.endTime;
    }

    public String toString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy h.mm a");
        StringBuffer endTimeBuffer = dateFormatter.format(endTime, new StringBuffer(), new FieldPosition(0));
        return endTimeBuffer.toString();
    }

    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && this.toString().equals(((EndTime) other).toString())); // state check
    }

    public int compareTo(EndTime other) {
        return endTime.compareTo(other.getEndTime());
    }

    public int hashCode() {
        return this.toString().hashCode();
    }
}
