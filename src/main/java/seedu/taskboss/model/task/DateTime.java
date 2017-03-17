package seedu.taskboss.model.task;

import java.util.Date;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.parser.DateTimeParser;

public class DateTime {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Task dates format should be in dd-mm-yyyy,"
            + " or word format like 5pm tomorrow, " + "and does not accept doubles.";

    public final String value;
    private boolean isDateInferred;
    private boolean isTimeInferred;
    private Date date;
    private DateTimeParser dtParser;

    public DateTime(String date) throws IllegalValueException {
        assert date != null;

        this.dtParser = new DateTimeParser();
        this.isDateInferred = false;
        this.isTimeInferred = false;
        this.date = null;
        String trimmedDate = date.trim();
        if (!isValidDateTime(trimmedDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }

        if (!trimmedDate.equals("")) {
            parseDateTime(trimmedDate);
        }
        this.value = date.toString();
    }

    public DateTime(Date dateTime, boolean isDateInferred, boolean isTimeInferred) {
        this.date = dateTime;
        this.isDateInferred = isDateInferred;
        this.isTimeInferred = isTimeInferred;
        value = this.date.toString();
    }

    /**
     * Returns if a given string is a valid date input.
     *
     * @throws IllegalValueException
     */
    public boolean isValidDateTime(String date) {
        if (date.equals("")) {
            return true;
        } else {
            return dtParser.isParseable(date);
        }
    }

    private void parseDateTime(String date) throws IllegalValueException {
        DateTime dateTime = dtParser.parseDate(date);
        this.date = dateTime.getDate();
        this.setIsDateInferred(dateTime.isDateInferred);
        this.setIsTimeInferred(dateTime.isTimeInferred);
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

    public boolean IsDateInferred() {
        return isDateInferred;
    }

    public boolean IsTimeInferred() {
        return isTimeInferred;
    }

    public void setIsDateInferred(boolean status) {
        this.isDateInferred = status;
    }

    public void setIsTimeInferred(boolean status) {
        this.isTimeInferred = status;
    }

    public Date getDate() {
        return date;
    }

}
