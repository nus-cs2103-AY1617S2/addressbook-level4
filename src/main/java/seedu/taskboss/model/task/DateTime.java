package seedu.taskboss.model.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.parser.DateTimeParser;

//@@author A0143157J
public class DateTime {

    public static final String ERROR_INVALID_DATE = "Failed to understand given date.";
    public static final String MESSAGE_DATE_CONSTRAINTS = "Task dates format should be in dd-mm-yyyy,"
            + " or word format like 5pm tomorrow, " + "and does not accept doubles.";
    private static final String EMPTY_STRING = "";
    private static final String NEWLINE = "\n";

    public String value;
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
        this.value = trimmedDate;
        if (!isValidDateTime(trimmedDate)) {
            throw new IllegalValueException(ERROR_INVALID_DATE + NEWLINE + MESSAGE_DATE_CONSTRAINTS);
        }

        if (trimmedDate.equals(EMPTY_STRING)) {
            this.value = EMPTY_STRING;
        } else {
            parseDateTime(trimmedDate);
            formatDateTime();
        }
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
    private boolean isValidDateTime(String date) throws IllegalValueException {
        if (date.equals(EMPTY_STRING)) {
            return true;
        } else {
            return dtParser.isParseable(date);
        }
    }

    public void parseDateTime(String date) throws IllegalValueException {
        if (date.equals(EMPTY_STRING)) {
            return;
        }
        DateTime dateTime = dtParser.parseDate(date);
        this.date = dateTime.getDate();
        this.setIsDateInferred(dateTime.isDateInferred);
        this.setIsTimeInferred(dateTime.isTimeInferred);
    }

    /**
     * Format the current natty-parsed Date into a string of our intended format
     * i.e May 19, 2017 8:30 PM
     */
    public String formatDateTime() {
        SimpleDateFormat sdfGeneral = new SimpleDateFormat("MMM dd, yyyy h:mm aa");
        SimpleDateFormat sdfNoTime = new SimpleDateFormat("MMM dd, yyyy");
        // No time provided by user
        if (this.isTimeInferred()) {
            this.value = sdfNoTime.format(this.date);
        } else if (!this.value.equals(EMPTY_STRING)) {
            this.value = sdfGeneral.format(this.date);
        }

        return this.value;
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

    public boolean isDateInferred() {
        return isDateInferred;
    }

    public boolean isTimeInferred() {
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
