package seedu.taskmanager.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.taskmanager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's start date in the task manager. Guarantees: immutable; is
 * valid as declared in {@link #isValidEndDate(String)}
 */
public class EndDate extends Date {

    public static final String MESSAGE_ENDDATE_CONSTRAINTS = "End date should be of dd/mm/yyyy format or "
            + "can be empty";
    public static final String ENDDATE_VALIDATION_REGEX = "(^$)|(^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$)";

    // @@author A0140032E
    private static final SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Validates given end date.
     *
     * @throws IllegalValueException
     *             if given end date string is invalid.
     */
    public EndDate(String endDate) throws IllegalValueException {
        super(endDateConstructor(endDate));
    }

    private static long endDateConstructor(String endDate) throws IllegalValueException {
        assert endDate != null;
        try {
            if (!isValidEndDate(endDate)) {
                throw new IllegalValueException(MESSAGE_ENDDATE_CONSTRAINTS);
            }
            if (endDate.trim().equals("")) {
                Calendar cal = Calendar.getInstance();
                return cal.getTimeInMillis();
            }
            return sdfInput.parse(endDate).getTime();
        } catch (IllegalValueException | ParseException e) {
            throw new IllegalValueException(MESSAGE_ENDDATE_CONSTRAINTS);
        }
    }
    // @@author

    /**
     * Returns if a given string is a valid task end date.
     */
    public static boolean isValidEndDate(String test) {
        return test.matches(ENDDATE_VALIDATION_REGEX);
    }

    // @@author A0140032E
    @Override
    public String toString() {
        return sdfInput.format(this);
    }
    // @@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndDate // instanceof handles nulls
                        && this.toString().equals(((EndDate) other).toString())); // state
                                                                                  // check
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
