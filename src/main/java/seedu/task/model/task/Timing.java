//@@author A0164212U
package seedu.task.model.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's timing in the address book. Guarantees: immutable; is valid
 * as declared in {@link #isValidTiming(String)}
 */
public class Timing implements Comparable<Timing> {

    public static final String MESSAGE_TIMING_CONSTRAINTS =
            "Task timing should be in the format HH:mm dd/MM/yyyy OR dd/MM/yyyy " +
                    "Use only HH:mm if today is the default date";
    public static final String[] TIMING_FORMAT = {"HH:mm dd/MM/yyyy", "dd/MM/yyyy"};
    public static final String NULL_TIMING = "n/a";
    public final String value;
    private Date timing;

    /**
     * Validates given timing.
     * Sets today's date as default if only time is specified
     *
     * @throws IllegalValueException
     *             if given timing string is invalid.
     */
    public Timing(String time) throws IllegalValueException {
        if (time != null) {
            String trimmedTiming = time.trim();
            if (!trimmedTiming.equals(NULL_TIMING) && trimmedTiming.length() <= 5) {
                trimmedTiming = trimmedTiming + " " + Timing.getTodayDate();
            }
            if (!isValidTiming(trimmedTiming)) {
                throw new IllegalValueException(MESSAGE_TIMING_CONSTRAINTS);
            }
            this.value = trimmedTiming;
            setTiming(trimmedTiming);
        } else {
            this.value = NULL_TIMING;
            this.timing = null;
        }
    }

    /**
     * Returns if a given string is a valid timing.
     */
    public static boolean isValidTiming(String test) {
        boolean isValid = false;
        if (test.equals(NULL_TIMING)) {
            isValid = true;
        } else {
            for (int i = 0; i < TIMING_FORMAT.length; i++) {
                SimpleDateFormat sdf = new SimpleDateFormat(TIMING_FORMAT[i]);
                sdf.setLenient(false);
                try {
                    // throws ParseException if timing is not valid
                    Date date = sdf.parse(test);
                    // check if year is truly 4 digits (the 'yyyy' regex does not support this)
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    if (cal.get(Calendar.YEAR) >= 1000 && cal.get(Calendar.YEAR) <= 9999) {
                        isValid = true;
                    }
                    break;
                } catch (ParseException e) {
                }
            }
        }
        return isValid;
    }

    public void setTiming(String time) {
        for (int i = 0; i < TIMING_FORMAT.length; i++) {
            SimpleDateFormat sdf = new SimpleDateFormat(TIMING_FORMAT[i]);
            sdf.setLenient(false);
            try {
                // throws ParseException if timing is not valid
                Date date = sdf.parse(time);
                this.timing = date;
                break;
            } catch (ParseException e) {
            }
        }
    }

    public Date getTiming() {
        return timing;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this.value == null) {
            return false;
        }
        return other == this // short circuit if same object
                || (other instanceof Timing // instanceof handles nulls
                        && this.value.equals(((Timing) other).value)); // state
        // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * @return returns today's date as a string in "dd/MM/yyyy" format
     */
    public static String getTodayDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String dateString = dateFormat.format(date);
        return dateString;
    }

    /**
     *
     * @param time1
     * @param time2
     * @return returns True if time1 is before time2
     */
    public static boolean checkTimingOrder(Timing time1, Timing time2) {
        boolean isOrdered = true;
        if (time1 == null || time1.value.equals(NULL_TIMING)
                || time2 == null || time2.value.equals(NULL_TIMING)) {
            return isOrdered;
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(time1.getTiming());
        cal2.setTime(time2.getTiming());

        if (cal1.compareTo(cal2) > 0) {
            isOrdered = false;
        }

        return isOrdered;
    }

    //@@author A0163559U
    /**
     * Results in Timing sorted in ascending order.
     */
    @SuppressWarnings("deprecation")
    @Override
    public int compareTo(Timing compareTiming) {
        boolean thisNull = this.timing == null;
        boolean otherNull = compareTiming.timing == null;

        if (thisNull && otherNull) {
            return 0;
        } else if (thisNull) {
            return 1;
        } else if (otherNull) {
            return -1;
        }

        boolean thisNullTiming = this.timing.equals(NULL_TIMING);
        boolean otherNullTiming = compareTiming.timing.equals(NULL_TIMING);

        if (thisNullTiming && otherNullTiming) {
            return 0;
        } else if (thisNullTiming) {
            return 1;
        } else if (otherNullTiming) {
            return -1;
        }

        int compareToResult = this.timing.getYear() - compareTiming.timing.getYear() + 3800;

        if (compareToResult == 0) {
            compareToResult = this.timing.getMonth() - compareTiming.timing.getMonth();
        }

        if (compareToResult == 0) {
            compareToResult = this.timing.getDay() - compareTiming.timing.getDay();
        }
        return compareToResult;
    }
}
