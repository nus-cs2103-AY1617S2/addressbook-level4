package seedu.todolist.model.task;

import java.util.Date;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import seedu.todolist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's end time in the to-do list.
 */

public class EndTime {
    
    public static final String MESSAGE_ENDTIME_CONSTRAINTS = 
            "End time should follow the format: DD-MM-YYYY TIME. E.g. \n"
            + "12-12-2008 3.30 PM";
    
    private Date _endTime;
    
    public EndTime(String endTime) throws IllegalValueException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy h.mm a");
        Date temp = dateFormatter.parse(endTime,new ParsePosition(0));
        if (temp == null) {
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        } else {
            _endTime = temp;
        }
    }
    
    public String toString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy h.mm a");
        StringBuffer endTimeBuffer = dateFormatter.format(_endTime, new StringBuffer(), new FieldPosition(0));
        return endTimeBuffer.toString();
    }
    
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && this.toString().equals(((EndTime) other).toString())); // state check
    }
    
    public int hashCode() {
        return this.toString().hashCode();
    }
}
