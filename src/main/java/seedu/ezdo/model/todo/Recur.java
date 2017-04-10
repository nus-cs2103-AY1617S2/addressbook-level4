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

    public static final HashMap<String, Integer> RECUR_INTERVALS = new HashMap<>();

    public final String value;

    /**
     * Adds a recurring time interval.
     * @param recur  The recur interval.
     * @throws IllegalValueException
     *             if given recurring time interval string is invalid.
     */
    public Recur(String recur) throws IllegalValueException {
        assert recur != null;
        initialiseRecurIntervals();  // initialises HashMap with respective intervals
        String trimmedRecur = recur.trim();
        checkIsValidRecur(trimmedRecur);
        this.value = trimmedRecur;
    }

    /**
     * Validates given recurring time interval.
     * @param recur     The recur interval.
     * @throws IllegalValueException
     *             if given recurring time interval string is invalid.
     */
    private void checkIsValidRecur(String recur) throws IllegalValueException {
        if (!isValidRecur(recur)) {
            throw new IllegalValueException(MESSAGE_RECUR_CONSTRAINTS);
        }
    }

    /**
     * Initialises recur intervals in RECUR_INTERVALS HashMap.
     */
    private void initialiseRecurIntervals() {

        String intervalDaily = "daily";
        String intervalWeekly = "weekly";
        String intervalMonthly = "monthly";
        String intervalYearly = "yearly";

        RECUR_INTERVALS.put(INTERVAL_NONE, Calendar.DATE);
        RECUR_INTERVALS.put(intervalDaily, Calendar.DAY_OF_MONTH);
        RECUR_INTERVALS.put(intervalWeekly, Calendar.WEEK_OF_MONTH);
        RECUR_INTERVALS.put(intervalMonthly, Calendar.MONTH);
        RECUR_INTERVALS.put(intervalYearly, Calendar.YEAR);
    }

    /**
     * Returns true if a given string is a valid recurring time interval.
     */
    public static boolean isValidRecur(String test) {
        return RECUR_INTERVALS.containsKey(test);
    }

    /**
     * Checks if a recurring interval is attached in a task.
     */
    public boolean isRecur() {
        return !value.equals(INTERVAL_NONE);
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
