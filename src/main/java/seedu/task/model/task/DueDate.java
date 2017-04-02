//@@author A0164103W
package seedu.task.model.task;

import java.util.Calendar;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.util.DateParser;

public class DueDate {
/**
 * Represents a Task's due date in the task list.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueDate(String)}
 */
    public final Calendar dueDate;

    /**
     * Validates given description.
     *
     * @throws IllegalValueException if given description string is invalid.
     */
    public DueDate(String inputDueDate) throws IllegalValueException {
        this.dueDate = DateParser.parse(inputDueDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueDate // instanceof handles nulls
                && this.dueDate.equals(((DueDate) other).dueDate)); // state check
    }


    @Override
    public String toString() {
        return DateParser.toString(dueDate);
    }

    @Override
    public int hashCode() {
        return dueDate.hashCode();
    }

}
