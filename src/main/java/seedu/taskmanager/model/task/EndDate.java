package seedu.taskmanager.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

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
    public static final LocalTime WORKING_HOUR_END = new LocalTime(18, 0);

    private static final SimpleDateFormat sdfOutput = new SimpleDateFormat("dd/MM/yyyy h:mm a");
    private static final SimpleDateFormat sdfOutputForDateString = new SimpleDateFormat("d MMM yyyy");
    private static final SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
    private static boolean timeInferred;

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
        try {
            return sdfInput.parse(endDate).getTime();
        } catch (ParseException e) {
            try {
                Parser parser = new Parser();
                List<DateGroup> dateGroups = parser.parse(endDate);
                Date parsedDate = dateGroups.get(0).getDates().get(0);
                timeInferred = dateGroups.get(0).isTimeInferred();
                if (timeInferred) {
                    DateTime dt = new DateTime(parsedDate);
                    dt = dt.withTime(WORKING_HOUR_END);
                    return dt.getMillis();
                }
                return dateGroups.get(0).getDates().get(0).getTime();
            } catch (IndexOutOfBoundsException f) {
                throw new IllegalValueException(MESSAGE_ENDDATE_CONSTRAINTS);
            }
        }
    }
    // @@author

    /**
     * Returns if a given string is a valid task end date.
     */
    public static boolean isValidEndDate(String test) {
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

    public String toDateString() {
        return sdfOutputForDateString.format(this);
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

    // @@author A0140032E
    public boolean isTimeInferred() {
        return timeInferred;
    }
    // @@author
}
