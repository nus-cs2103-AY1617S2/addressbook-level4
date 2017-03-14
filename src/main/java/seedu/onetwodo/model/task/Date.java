package seedu.onetwodo.model.task;


import seedu.onetwodo.commons.exceptions.IllegalValueException;

import java.time.*;
import java.util.Optional;


/**
 * Represents a Task's date in the toDo list.
 * Guarantees: immutable.
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "If task date exists, it must be a valid date";

    public String value;  // value to be displayed to user
    protected Optional<LocalDateTime> localDateTime;
    
    // to be used if no date OR time is specified.
    protected LocalDateTime defaultDateTime = LocalDateTime.now(); 

    /**
     * Create a date.
     *
     * @throws IllegalValueException if given date toDo string is invalid.
     */
    public Date(String input) throws IllegalValueException {
        assert input != null;
        this.value = input;
    }

    /**
     * 
     * @param localDateTime input optional LocalDateTime
     * @return String to be displayed to user.
     */
    public String createDisplayValue(Optional<LocalDateTime> localDateTime) {
        if(!localDateTime.isPresent()) {
            return "";
        } else {
            return localDateTime.get().toString();
        }
    }
    
    public Date getDate() {
        return this;
    }
    
    public boolean hasDate() {
        return this.localDateTime.isPresent();
    }
    
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
