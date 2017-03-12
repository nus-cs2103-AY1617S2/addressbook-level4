package seedu.todolist.model.task;

import java.util.Date;

import java.text.DateFormat;
import java.text.ParseException;

import seedu.todolist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's end time in the to-do list.
 */

public class EndTime {
    
    public static final String MESSAGE_ENDTIME_CONSTRAINTS = 
            "End time should follow the format: DD/MM/YYYY TIME. E.g. \n"
            + "12/12/2008 3.30 PM";
    
    private Date _endTime;
    
    public EndTime(String endTime) throws IllegalValueException {
        try {
            DateFormat dateFormatter = DateFormat.getDateInstance();
            _endTime = dateFormatter.parse(endTime);
        } catch (ParseException e) {
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        }
    }
    
    public String toString() {
        DateFormat dateFormatter = DateFormat.getDateInstance();
        return dateFormatter.format(_endTime);
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
