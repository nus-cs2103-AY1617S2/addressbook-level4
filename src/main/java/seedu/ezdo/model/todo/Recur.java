package seedu.ezdo.model.todo;

import java.util.Calendar;
import java.util.HashMap;

import seedu.ezdo.commons.exceptions.IllegalValueException;

//@@author A0139177W
/**
 * Represents a Task's recurring interval in ezDo. Guarantees: immutable; is
 * valid as declared in {@link #isValidRecurring(String)}
 */
public class Recur {

    public static final String MESSAGE_RECUR_CONSTRAINTS =
            "Recurring time interval should be 'daily', 'weekly', 'monthly' or 'yearly'.";

    public static final String INTERVAL_NONE = "";
    public static final String INTERVAL_DAILY = "daily";
    public static final String INTERVAL_WEEKLY = "weekly";
    public static final String INTERVAL_MONTHLY = "monthly";
    public static final String INTERVAL_YEARLY = "yearly";

    public static final HashMap<String, Integer> RECUR_INTERVALS = new HashMap<>();

    public final String value;

    /**
     * Validates given recurring time interval.
     *
     * @throws IllegalValueException
     *             if given recurring time interval string is invalid.
     */
    public Recur(String recur) throws IllegalValueException {
        initialiseRecurIntervals();
        assert recur != null;
        String trimmedRecur = recur.trim();
        if (!isValidRecur(trimmedRecur)) {
            throw new IllegalValueException(MESSAGE_RECUR_CONSTRAINTS);
        }
        this.value = trimmedRecur;
    }

    private void initialiseRecurIntervals() {
        RECUR_INTERVALS.put(INTERVAL_NONE, Calendar.DATE);
        RECUR_INTERVALS.put(INTERVAL_DAILY, Calendar.DAY_OF_MONTH);
        RECUR_INTERVALS.put(INTERVAL_WEEKLY, Calendar.WEEK_OF_MONTH);
        RECUR_INTERVALS.put(INTERVAL_MONTHLY, Calendar.MONTH);
        RECUR_INTERVALS.put(INTERVAL_YEARLY, Calendar.YEAR);
    }

    /**
     * Returns true if a given string is a valid recurring time interval.
     */
    public static boolean isValidRecur(String test) {
        return RECUR_INTERVALS.containsKey(test);
    }

    public boolean isRecur() {
        return !value.equals("");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Recur // instanceof handles nulls
                        && this.value.equals(((Recur) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
