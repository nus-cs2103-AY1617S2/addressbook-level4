package seedu.tache.model.task;

import seedu.tache.commons.exceptions.IllegalValueException;

public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Task date should only contain <CONSTRAINT>";

    public final String date;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        assert date != null;
        String trimmedStartDate = date.trim();
        /*if (!isValidDate(trimmedStartDate)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }*/
        this.date = trimmedStartDate;
    }

    /**
     * Returns true if a given string is a valid task date.
     */
    /*public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }*/

    @Override
    public String toString() {
        return date;
    }

   /* @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.time.equals(((Date) other).time)); // state check
    }*/

    /*@Override
    public int hashCode() {
        return (startDate.hashCode() && endDate.hashCode());
    }*/

}
