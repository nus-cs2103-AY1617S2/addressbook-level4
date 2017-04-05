//@@author A0105748B
package seedu.bulletjournal.model.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.bulletjournal.commons.exceptions.IllegalValueException;
import seedu.bulletjournal.logic.parser.DateParser;

/**
 * Represents a Task's due date in the todo list.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueDate(String)}
 */
public class DueDate {

    public static final String MESSAGE_DUEDATE_CONSTRAINTS =
            "Unrecognised date and time for due date!";

    private static DateFormat outputFormatter = new SimpleDateFormat("dd-MMM-yyyy, HH:mm (EEE)");

    /*
     * The first character of the due date must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DUEDATE_VALIDATION_REGEX = "[^\\s].*";

    public final Date value;

    /**
     * Validates given due date.
     *
     * @throws IllegalValueException if given due date string is invalid.
     */
    public DueDate(String due) throws IllegalValueException {
        assert due != null;
        String trimmedDue = due.trim();
        if (!isValidDueDate(trimmedDue)) {
            throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
        }
        this.value = DateParser.parse(trimmedDue);
        if (this.value == null) {
            throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid task phone number.
     */
    public static boolean isValidDueDate(String test) {
        return test.matches(DUEDATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        String dueDateString = outputFormatter.format(value);
        return dueDateString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueDate // instanceof handles nulls
                && this.value.equals(((DueDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
