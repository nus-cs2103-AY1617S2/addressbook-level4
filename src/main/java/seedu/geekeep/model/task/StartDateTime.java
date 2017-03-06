package seedu.geekeep.model.task;

import java.time.LocalDateTime;
import java.util.Calendar;

import seedu.geekeep.commons.exceptions.InvalidDateTimeException;

/**
 * Represents the starting datetime of a task. Guarantees: immutable; is valid as declared in
 * {@link #isValidDateTime()}
 */
public class StartDateTime {

    /**
     * Validates given dateTime.
     *
     * @throws InvalidDateTimeException
     *             if given datetime is invalid.
     */
    public static boolean isValidDateTime(LocalDateTime dateTime) {
        Calendar cal = Calendar.getInstance();
        cal.setLenient(false);
        cal.set(dateTime.getYear(),
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getHour(),
                dateTime.getMinute());
        try {
            cal.getTime();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public final LocalDateTime dateTime;

    public StartDateTime(int year, int month, int day, int hour, int minute) throws InvalidDateTimeException{
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);
        if(!isValidDateTime(dateTime)) {
            throw new InvalidDateTimeException();
        }
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDateTime // instanceof handles nulls
                        && this.dateTime.equals(((StartDateTime) other).dateTime)); // state check
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }

    @Override
    public String toString() {
        return dateTime.toString();
    }
}
