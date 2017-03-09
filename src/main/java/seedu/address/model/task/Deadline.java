package seedu.address.model.task;

import java.util.Date;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.DateTimeParser;

/**
 * Represents a Task's deadline in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
            "Deadline can have zero or more characters";

    /*
     * The deadline must have at least one visible character
     * TODO: change regex once deadline can be translated to a date
     */
    public static final String DEADLINE_VALIDATION_REGEX = ".*";

    private Date deadline;

    /**
     * Validates given deadline.
     *
     * @throws IllegalValueException if given deadline string is invalid.
     */
    public Deadline(String strDeadline) throws IllegalValueException {
        assert strDeadline != null;
        List<Date> dateList = new DateTimeParser().parse(strDeadline).get(0).getDates();
        if (!isValidDeadline(strDeadline) && dateList.size() == 0) {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
        this.deadline = dateList.get(0);
    }

    /**
     * Returns true if a given string is a valid deadline.
     */
    public static boolean isValidDeadline(String test) {
        return test.matches(DEADLINE_VALIDATION_REGEX);
    }
    
    public Date getDateTime() {
        return deadline;
    }

    @Override
    public String toString() {
        return deadline.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.deadline.equals(((Deadline) other).deadline)); // state check
    }

    @Override
    public int hashCode() {
        return deadline.hashCode();
    }

}
