//@@author A0163744B
package seedu.task.model.task;

import java.util.Calendar;
import java.util.Objects;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.util.DateParser;

/**
 * Represents a Task's duration in the task list. Uses a start and end date
 * Guarantees: immutable; start date is before end date.
 */
public class Duration {
    public static final String START_DATE_AFTER_END_DATE =
            "The start date of a duration must be before the end date";

    public final Calendar start;
    public final Calendar end;

    /**
     * Validates given duration.
     *
     * @throws IllegalValueException if given duration string is invalid.
     */
    public Duration(String start, String end) throws IllegalValueException {
        assert start != null && end != null;
        this.start = DateParser.parse(start);
        this.end = DateParser.parse(end);
        if (this.start.after(this.end)) {
            throw new IllegalValueException(START_DATE_AFTER_END_DATE);
        }
    }

    public String getStartString() {
        return DateParser.toString(this.start);
    }

    public String getEndString() {
        return DateParser.toString(this.end);
    }

    @Override
    public String toString() {
        return "Start: " + DateParser.toString(this.start) + " End: " + DateParser.toString(this.end);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Duration // instanceof handles nulls
                && this.start.equals(((Duration) other).start) // state check
                && this.end.equals(((Duration) other).end)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(start.hashCode(), end.hashCode());
    }

}
