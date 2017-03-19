package seedu.task.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's date in the address book. Guarantees: immutable; is valid
 * as declared in {@link #isValidTiming(String)}
 */
public class Timing implements Comparable<Timing> {

    public static final String MESSAGE_TIMING_CONSTRAINTS = "Task date should be in the format HH:mm dd/MM/yyyy OR dd/MM/yyyy";
    public static final String[] TIMING_FORMAT = {
            // "dd/MM/yyyy HH:mm",
            "HH:mm dd/MM/yyyy", "dd/MM/yyyy" };

    public final String value;
    private Date date;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException
     *             if given date string is invalid.
     */
    public Timing(String timing) throws IllegalValueException {
        if (timing != null) {
            String trimmedTiming = timing.trim();
            if (!isValidTiming(trimmedTiming)) {
                throw new IllegalValueException(MESSAGE_TIMING_CONSTRAINTS);
            }
            this.value = trimmedTiming;
        } else {
            this.value = "n/a";
        }
    }

    /**
     * Returns if a given string is a valid date.
     */
    private boolean isValidTiming(String test) {
        boolean isValid = false;
        if (test.equals("n/a")) {
            isValid = true;
        } else {
            for (int i = 0; i < TIMING_FORMAT.length; i++) {
                SimpleDateFormat sdf = new SimpleDateFormat(TIMING_FORMAT[i]);
                sdf.setLenient(false);
                try {
                    // throws ParseException if date is not valid
                    Date date = sdf.parse(test);
                    isValid = true;
                    this.date = date;
                    break;
                } catch (ParseException e) {
                }
            }
        }
        return isValid;
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
     * Results in Timing sorted in ascending order.
     */
    @Override
    public int compareTo(Timing compareTiming) {
        return this.date.compareTo(compareTiming.date);
    }

}
