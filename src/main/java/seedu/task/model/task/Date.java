package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.DateUtil;

/**
 * Represents a Date in the YTomorrow.
 */
public abstract class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Task date's format should resemble a date";

    protected  String inputValue;
    protected java.util.Date value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {

        inputValue = date;

        if (date != null) {
            value = DateUtil.parse(date);
        } else {
            value = new java.util.Date();
        }
    }

    public java.util.Date getTime() {
        return getValue();
    }

    public String getInputValue() {
        return inputValue;
    }

    public java.util.Date getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getInputValue();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Date
                        && this.getValue().equals(((Date) other).getValue()));
    }
    //@@author A0164032U
    public static boolean isValidDate(String date) {
        return DateUtil.isValidDate(date);
    }

    @Override
    public int hashCode() {
        return getInputValue().hashCode() * getValue().hashCode();
    }

}
