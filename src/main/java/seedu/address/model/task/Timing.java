package seedu.address.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTiming(String)}
 */
public class Timing {

    public static final String MESSAGE_TIMING_CONSTRAINTS =
            "Task date should be in the format HH:mm dd/MM/yyyy OR dd/MM/yyyy";
    public static final String[] timing_format = {
//    		"dd/MM/yyyy HH:mm",
    		"HH:mm dd/MM/yyyy",
    		"dd/MM/yyyy"
    };
    
    public final String value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Timing(String timing) throws IllegalValueException {
        if (timing != null) {
            String trimmedTiming = timing.trim();
            if (!isValidTiming(trimmedTiming)) {
                throw new IllegalValueException(MESSAGE_TIMING_CONSTRAINTS);
            }
            this.value = trimmedTiming;
        } else {
            this.value = null;
        }
    }

    /**
     * Returns if a given string is a valid date.
     */
    public static boolean isValidTiming(String test) {
    	boolean isValid = false;
        for(int i=0; i<timing_format.length; i++){
        	SimpleDateFormat sdf = new SimpleDateFormat(timing_format[i]);
            sdf.setLenient(false);
	        try {
	            //throws ParseException if date is not valid
	            sdf.parse(test);
	            isValid = true;
	            break;
	        } catch (ParseException e) {
	        }
        }
        return isValid;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this.value == null) {
            return false;
        }
        return other == this // short circuit if same object
                || (other instanceof Timing // instanceof handles nulls
                && this.value.equals(((Timing) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
