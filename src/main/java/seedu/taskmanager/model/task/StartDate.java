package seedu.taskmanager.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.taskmanager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's start date in the task manager. Guarantees: immutable; is
 * valid as declared in {@link #isValidStartDate(String)}
 */

public class StartDate extends Date {

    public static final String MESSAGE_STARTDATE_CONSTRAINTS = "Start date should be of dd/mm/yyyy format "
            + "or can be empty";
    public static final String STARTDATE_VALIDATION_REGEX = "(^$)|(^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])"
            + "/[0-9]{4}$)";

    // @@author A0140032E
    private static final SimpleDateFormat sdfOutput = new SimpleDateFormat("dd/MM/yyyy h:mm a");
    private static final SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");

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
        try {
            return sdfInput.parse(startDate).getTime();
        } catch (ParseException e) {
            try {
                Parser parser = new Parser();
                List<DateGroup> dateGroups = parser.parse(startDate);
                return dateGroups.get(0).getDates().get(0).getTime();
            } catch (IndexOutOfBoundsException f) {
                throw new IllegalValueException(MESSAGE_STARTDATE_CONSTRAINTS);
            }
        }
    }
    // @@author

    /**
     * Returns true if a given string is a valid task start date with the natty
     * parser.
     */
    public static boolean isValidStartDate(String test) {
        Parser parser = new Parser();
        List<DateGroup> dateGroups = parser.parse(test);
        return !(dateGroups.isEmpty()) && !(dateGroups.get(0).getDates().isEmpty());
    }

    // @@author A0140032E
    @Override
    public String toString() {
        return sdfOutput.format(this);
    }

    public String toFullDateString() {
        return super.toString();
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
