package seedu.address.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;

public class DateTime {

    /*
     * Represents a Task's datetime object in the task manager.
     * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
     */

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Task datetime should be in the format of dd/mm/yyyy";

    public final Date dateTime;
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Validates given dateTime.
     *
     * @throws IllegalValueException if given dateTime is invalid.
     */
    public DateTime(Date dateTime) throws IllegalValueException {
        assert dateTime != null;
        if (!isValidDateTime(dateTime)) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        this.dateTime = dateTime;
    }

    public DateTime(String dateTime) throws IllegalValueException {
        assert dateTime != null;
        Date dateTimeObj = null;
        try {
            dateTimeObj = formatter.parse(dateTime);
            if (!isValidDateTime(dateTimeObj)) {
                throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        this.dateTime = dateTimeObj;
    }

    /**
     * Returns true if a given date is a valid task dateTime.
     */
    public static boolean isValidDateTime(Date test) {
        return test.after(new Date());
    }

    public static boolean isValidDateTime(String test) {
        try {
            return formatter.parse(test).after(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {
        return formatter.format(dateTime);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && this.dateTime.equals(((DateTime) other).dateTime)); // state check

    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }
}
