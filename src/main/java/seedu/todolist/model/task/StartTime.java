package seedu.todolist.model.task;

import java.util.Date;

import java.text.DateFormat;
import java.text.ParseException;

import seedu.todolist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's start time in the to-do list.
 */

public class StartTime {
    
    public static final String MESSAGE_STARTTIME_CONSTRAINTS = 
            "Start time should follow the format: DD/MM/YYYY TIME. E.g. \n"
            + "12/12/2008 3.30 PM";
    
    private Date _startTime;
    
    public StartTime(String startTime) throws IllegalValueException {
        try {
            DateFormat dateFormatter = DateFormat.getDateInstance();
            _startTime = dateFormatter.parse(startTime);
        } catch (ParseException e) {
            throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
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
