package seedu.address.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;

public class DateTime {

    /*
     * Represents a Task's dateTime in the task manager.
     * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
     */

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Task date time should be in the format of dd/mm/yyyy";

    public final Date dateTime;
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Validates given dateTime.
     *
     * @throws IllegalValueException if given dateTime is invalid.
     */
    public DateTime(Date dateTime) throws IllegalValueException {
        assert dateTime != null;
        this.dateTime = dateTime;
    }

    public DateTime(String dateTime) throws IllegalValueException {
        assert dateTime != null;
        Date dateTimeObj = null;
        try {
            dateTimeObj = formatter.parse(dateTime);
        } catch (ParseException e) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        this.dateTime = dateTimeObj;
    }

    /**
     * Returns true if a given date is a valid task dateTime.
     * @throws IllegalValueException 
     */
    public static boolean isValidDateTime(String test) {
        try {
        	if(formatter.parse(test) != null) {
                return true;
            } 
        } catch (ParseException pe) {
            try {
                throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
			} catch (IllegalValueException e) {
			    
		    }
        }
	    return false;
    }

    @Override
    public String toString() {
        return formatter.format(dateTime);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && this.dateTime.equals(((DateTime) other).dateTime)); // state check

    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }
}
