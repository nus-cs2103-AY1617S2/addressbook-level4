package seedu.taskboss.model.task;

import java.util.Date;

import seedu.taskboss.commons.exceptions.IllegalValueException;

public class DateTime {

    private static final int EMPTY = 0;

    public static final String MESSAGE_DATE_CONSTRAINTS = "Task dates format should be in dd-mm-yyyy,"
            + " or word format like 5pm tomorrow, " + "and does not accept doubles.";

    public final String value;
    private final boolean isDateInferred;
    private final boolean isTimeInferred;
    private Date dateTime;

    public DateTime(String date) throws IllegalValueException {
        assert date != null;
        isDateInferred = false;
        isTimeInferred = false;
        dateTime = null;
        String trimmedDate = date.trim();
        if (!isValidDateTime(trimmedDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.value = trimmedDate;
    }

    public DateTime(Date dateTime, boolean isDateInferred, boolean isTimeInferred) {
        this.dateTime = dateTime;
        this.isDateInferred = isDateInferred;
        this.isTimeInferred = isTimeInferred;
        value = this.dateTime.toString();
    }

    /**
     * Returns if a given string is not empty.
     *
     * @throws IllegalValueException
     */
    public boolean isValidDateTime(String date) {
        //return (date.length() > EMPTY || "".equals(date));
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                        && this.value.equals(((DateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public boolean getIsDateInferred() {
        return isDateInferred;
    }

    public boolean getIsTimeInferred() {
        return isTimeInferred;
    }

}
