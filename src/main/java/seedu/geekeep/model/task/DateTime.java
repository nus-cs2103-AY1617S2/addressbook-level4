package seedu.geekeep.model.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.geekeep.commons.exceptions.IllegalValueException;

//@@author A0121658E
/**
 * Represents the ending date and time of a task. Guarantees: immutable; is valid as declared in
 * {@link #isValidDateTime()}
 */
public class DateTime {

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Date and time format should be in this format: DD-MM-YY [HHMM]";
    public static final String DATETIME_VALIDATION_REGEX = "\\d{2}-\\d{2}-\\d{2}(\\s{1}\\d{4})?";
    public static final String DATETIME_REGEX_WITHOUT_TIME = "\\d{2}-\\d{2}-\\d{2}";
    public static final String DEFAULT_TIME = "2359";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yy HHmm");

    //@@author A0148037E
    //with the constraint of DD-MM-YY, the max date time should be 2099-12-31 2359
    //and the min date time should be 2000-01-01 0000;
    public static final String MAX_TIME = "31-12-99 2359";
    public static final String MIN_TIME = "01-01-00 0000";
    //@@author

    public final LocalDateTime dateTime;
    public final String value;

    /**
     *  Validates given dateTime.
     */
    public static boolean isValidDateTime(String test) {
        return test.matches(DATETIME_VALIDATION_REGEX);
    }

    public static boolean isValidDateTimeWithoutTime(String test) {
        return test.matches(DATETIME_REGEX_WITHOUT_TIME);
    }

    //@@author A0148037E
    /**
     * Returns the min date time GeeKeep currently supports
     */
    public static DateTime getMin() {
        DateTime minDateTime = null;

        //It is guaranteed that there is no exception thrown
        try {
            minDateTime =  new DateTime(MIN_TIME);
        } catch (IllegalValueException e) {
        }
        return minDateTime;
    }

    /**
     * Returns the max date time GeeKeep currently supports
     */
    public static DateTime getMax() {
        DateTime maxDateTime = null;

      //It is guaranteed that there is no exception thrown
        try {
            maxDateTime =  new DateTime(MAX_TIME);
        } catch (IllegalValueException e) {
        }
        return maxDateTime;
    }
    //@@author

    public DateTime(String dateTimeString) throws IllegalValueException {
        if (!isValidDateTime(dateTimeString)) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        this.value = dateTimeString;
        if (isValidDateTimeWithoutTime(dateTimeString)) {
            dateTimeString = dateTimeString + " " + DEFAULT_TIME;
        }
        this.dateTime = LocalDateTime.parse(dateTimeString, FORMATTER);
    }

    //@@author A0148037E
    public int compare(DateTime otherTime) {
        return dateTime.compareTo(otherTime.dateTime);
    }

    //@@author A0121658E
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                        && this.value.equals(((DateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }

}
