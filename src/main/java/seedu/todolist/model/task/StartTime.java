package seedu.todolist.model.task;

import java.util.Date;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import seedu.todolist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's start time in the to-do list.
 */

public class StartTime {
    
    public static final String MESSAGE_STARTTIME_CONSTRAINTS = 
            "Start time should follow the format: DD-MM-YYYY TIME. E.g. \n"
            + "12-12-2008 3.30 PM";
    
    private Date _startTime;
    
    public StartTime(String startTime) throws IllegalValueException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy h:mm a");
        Date temp = dateFormatter.parse(startTime,new ParsePosition(0));
        if (temp == null) {
            throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
        } else {
            _startTime = temp;
        }
    }
    
    public String toString() {
        DateFormat dateFormatter = DateFormat.getDateInstance();
        return dateFormatter.format(_startTime);
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
