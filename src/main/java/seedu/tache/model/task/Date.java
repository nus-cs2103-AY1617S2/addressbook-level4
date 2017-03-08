package seedu.tache.model.task;

import seedu.tache.commons.exceptions.IllegalValueException;

public class Date {
    
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Task date should only contain <CONSTRAINT>";
    
    public final String startDate;
    public final String endDate;
    
    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Date(String startDate, String endDate) throws IllegalValueException {
        assert startDate != null;
        String trimmedStartDate = startDate.trim();
        /*if (!isValidDate(trimmedStartDate)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }*/
        this.startDate = trimmedStartDate;
        
        assert endDate != null;
        String trimmedEndDate = endDate.trim();
        /*if (!isValidDate(trimmedEndDate)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }*/
        this.endDate = trimmedEndDate;
    }

    /**
     * Returns true if a given string is a valid task date.
     */
    /*public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }*/

    @Override
    public String toString() {
        return "Start: "+startDate + " End: " + endDate;
    }

   /* @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.time.equals(((Date) other).time)); // state check
    }*/

    /*@Override
    public int hashCode() {
        return (startDate.hashCode() && endDate.hashCode());
    }*/

}
