//@@author A0139343E
package seedu.onetwodo.model.task;

import seedu.onetwodo.commons.exceptions.IllegalValueException;

enum RecurPeriod {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}

 /**
 * Represents if a task is recurring in the toDo list.
 */
public class Recurring {

    public static final String RECURRING_CONSTRAINTS = "Task can only recur daily, weekly, monthly and yearly";
    public static final String RECUR_DAILY = "daily";
    public static final String RECUR_WEEKLY = "weekly";
    public static final String RECUR_MONTHLY = "monthly";
    public static final String RECUR_YEARLY = "yearly";
    public static final String RECUR_EMPTY = "";

    public String value;

    private RecurPeriod period;

    /**
     * Validates given recurring value.
     *
     * @throws IllegalValueException if given string is invalid.
     */
    public Recurring(String recur) throws IllegalValueException {
        assert recur != null;
        String lowerCaseRecur = recur.toLowerCase().trim();
        setupRecurPeriod(lowerCaseRecur);
    }

    /**
     * Checks if user input a valid recurring period.
     * Setup period value.
     */
    public void setupRecurPeriod(String userInput) throws IllegalValueException {
        switch (userInput) {
        case RECUR_DAILY:
            this.period = RecurPeriod.DAILY;
            break;
        case RECUR_WEEKLY:
            this.period = RecurPeriod.WEEKLY;
            break;
        case RECUR_MONTHLY:
            this.period = RecurPeriod.MONTHLY;
            break;
        case RECUR_YEARLY:
            this.period = RecurPeriod.YEARLY;
            break;
        case RECUR_EMPTY:
            this.period = null;
            break;
        default:
            throw new IllegalValueException(RECURRING_CONSTRAINTS);
        }
        if (this.period == null) {
            this.value = "";
        } else {
            this.value = this.getRecur().toString().toLowerCase().trim();
        }
    }

    public RecurPeriod getRecur() {
        return this.period;
    }

    public boolean hasRecur() {
        return this.getRecur() != null;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Recurring // instanceof handles nulls
                        && this.toString().equals(((Recurring) other).toString())); // state check
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

}
