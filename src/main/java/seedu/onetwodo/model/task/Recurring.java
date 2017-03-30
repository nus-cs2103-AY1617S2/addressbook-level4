package seedu.onetwodo.model.task;

import seedu.onetwodo.commons.exceptions.IllegalValueException;

//@@author A0139343E
/**
 * Represents if a task is recurring in the toDo list.
 * Guarantees: immutable; is valid as declared in {@link #isValidRecurring(String)}
 */
public class Recurring {
    public static final String RECURRING_CONSTRAINTS = "Task can only recur daily, weekly and monthly";
    public static final String RECUR_DAILY = "daily";
    public static final String RECUR_WEEKLY = "weekly";
    public static final String RECUR_MONTHLY = "monthly";
    public static final String RECUR_YEARLY = "yearly";

    public String value;

    /**
     * Validates given recurring value.
     *
     * @throws IllegalValueException if given string is invalid.
     */
    public Recurring(String recur) throws IllegalValueException {
        assert recur != null;
        String lowerCaseRecur = recur.toLowerCase().trim();
        if (!isValidRecurring(lowerCaseRecur)) {
            throw new IllegalValueException(RECURRING_CONSTRAINTS);
        } else {
            value = lowerCaseRecur;
        }
    }

    /**
     * Checks if user input a valid recurring period.
     */
    public static boolean isValidRecurring(String userInput) {
        return userInput.isEmpty() ||
               userInput.equals(RECUR_DAILY) ||
               userInput.equals(RECUR_WEEKLY) ||
               userInput.equals(RECUR_MONTHLY) ||
               userInput.equals(RECUR_YEARLY);
    }

    public boolean hasRecur() {
        return !value.trim().isEmpty();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Recurring // instanceof handles nulls
                        && this.value.equals(((Recurring) other).value)); // state
                                                                         // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
