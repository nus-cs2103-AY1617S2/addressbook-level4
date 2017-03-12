package typetask.model.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import typetask.commons.exceptions.IllegalValueException;

public class DateTime {
    private Date date; 
    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Task's date should be in this format dd-mm-yy HH:MM";
    public static final String DATE_VALIDATION_REGEX = "[\\d]+-[\\d]+-[\\d]+"; 
    private static DateFormat outputFormatter = new SimpleDateFormat("dd-MMM-yy HH:mm"); 
  
    /**
     * Converts the string that contains date information into Date
     * 
     * @throws IllegalValueException if the format of date is unrecognised
     */
    public DateTime(String date) throws IllegalValueException {
       this.date = DateParser.parse(date);
       if (this.date == null) {
           throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
       }
    }

    /**
     * Compares the current date/time with the object's date/time.
     * @returns
     * value 0 if current Date is equal to given Date 
     * value > 0 if current Date is after the given Date
     * value < 0 if current Date is before the given Date 
     * 
     */
	public int compareTo(DateTime dateTime) {
		return date.compareTo(dateTime.getDate());
	}
	
	public int compareTo(Date date) {
		return this.date.compareTo(date);
	}
	
    /**
     * Returns true if a given string is a valid task date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }
    
	public Date getDate() {
		return date;
	}
	
    @Override
    public String toString() {
        String value = outputFormatter.format(date);
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && this.date.equals(((DateTime) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
