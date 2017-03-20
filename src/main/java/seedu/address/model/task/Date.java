// @@author A0163996J

package seedu.address.model.task;

import com.joestelmach.natty.*;

import seedu.address.commons.exceptions.IllegalValueException;

import java.util.List;

/**
 * Represents a Task's start or end date and time in the task manager. Guarantees: immutable; is
 * valid as declared in {@link #isValidDate(String)}
 */
public class Date {
	  public static final String MESSAGE_TITLE_CONSTRAINTS = "Task dates should be complete dates";

	    /*
	     * The first character of the task must not be a whitespace, otherwise
	     * " " (a blank string) becomes a valid input.
	     */
	    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

	    public final java.util.Date date;
	    public final String dateString;
	    Parser parser = new Parser();

	    
	    public Date() {
	    	this.date = null;
	    	this.dateString = null;
	    }
	    
	    /**
	     * Validates given date.
	     *
	     * @throws IllegalValueException
	     *             if given date string is invalid.
	     */
	    public Date(String date) throws IllegalValueException {
	        //assert date != null;
	        //String trimmedDate= date.trim();

	        // if (!isValidTitle(trimmedName)) {
	        // throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
	        // }
	    	this.dateString = date;
	    	System.out.println(date);
	    	if (date != null && date.length() > 0) {
	    		List groups = parser.parse(date);
	    		if (groups.size() >= 0) {
		    		DateGroup group = (DateGroup)groups.get(0);
		    		this.date = group.getDates().get(0);
	    		}
	    		else {
	    			this.date = null;
	    		}
	    	}
	    	else {
	    		this.date = null;
	    	}
	    }

	    /**
	     * Returns true if a given string is a valid person name.
	     */
	    public static boolean isValidDate(String test) {
	        return test.matches(NAME_VALIDATION_REGEX);
	    }

	    @Override
	    public String toString() {
	    	if (date != null) {
	    		return date.toString();
	    	}
	    	else {
	    		return "";
	    	}
	    }
	    
	    public String originalString() {
	    	if (dateString != null) {
	    		return dateString;
	    	}
	    	else {
	    		return "";
	    	}
	    }

	    @Override
	    public boolean equals(Object other) {
	    	if (this.date == null) {
	    		if (((Date) other).date == null) {
	    			return true;
	    		}
	    		else {
	    			return false;
	    		}
	    	}
	        return other == this // short circuit if same object
	                || (other instanceof Date // instanceof handles nulls
	                        && this.date.equals(((Date) other).date)); // state
	                                                                           // check
	    }

	    @Override
	    public int hashCode() {
	        return date.hashCode();
	    }

}
