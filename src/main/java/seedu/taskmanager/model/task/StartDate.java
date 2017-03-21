package seedu.taskmanager.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.taskmanager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's start date in the task manager. Guarantees: immutable; is
 * valid as declared in {@link #isValidStartDate(String)}
 */
public class StartDate extends Date {
    private final static SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");

    public static final String MESSAGE_STARTDATE_CONSTRAINTS = "Start date should be of dd/mm/yyyy format "
            + "or can be empty";
    public static final String STARTDATE_VALIDATION_REGEX = "(^$)|(^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])"
            + "/[0-9]{4}$)";

    // @@author A0140032E
    

    /**
     * Validates given start date.
     *
     * @throws IllegalValueException
     *             if given start date is invalid.
     */
    public StartDate(String startDate) throws IllegalValueException {
        super(startDateConstructor(startDate));
    }

    private static long startDateConstructor(String startDate) throws IllegalValueException {
        assert startDate != null;
        if (!isValidStartDate(startDate)) {
            throw new IllegalValueException(MESSAGE_STARTDATE_CONSTRAINTS);
        }
        try {
            return sdfInput.parse(startDate).getTime();
        } catch (ParseException e) {
            throw new IllegalValueException(MESSAGE_STARTDATE_CONSTRAINTS);
        }
    }
    // @@author

    /**
     * Returns true if a given string is a valid task start date.
     */
    public static boolean isValidStartDate(String test) {
        return test.matches(STARTDATE_VALIDATION_REGEX);
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
                || (other instanceof StartDate // instanceof handles nulls
                        && this.toString().equals(((StartDate) other).toString())); // state
        // check
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
