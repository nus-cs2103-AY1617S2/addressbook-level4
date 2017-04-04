//@@author A0105748B
package seedu.bulletjournal.model.task;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.bulletjournal.commons.exceptions.IllegalValueException;
import seedu.bulletjournal.logic.parser.DateParser;

/**
 * Represents a Task's begin date in the bullet journal.
 * Guarantees: immutable; is valid as declared in {@link #isValidBeginDate(String)}
 */
public class BeginDate {

    public static final String MESSAGE_BEGINDATE_CONSTRAINTS =
            "Unrecognised date and time for begin date!";

    private static DateFormat outputFormatter = new SimpleDateFormat("dd-MMM-yyyy, HH:mm (EEE)");

    /*
     * The first character of the begin date must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String BEGINDATE_VALIDATION_REGEX = "[^\\s].*";

    public final Date value;

    /**
     * Validates given begin date.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public BeginDate(String beginDate) throws IllegalValueException {
        assert beginDate != null;
        if (!isValidBeginDate(beginDate)) {
            throw new IllegalValueException(MESSAGE_BEGINDATE_CONSTRAINTS);
        }
        this.value = DateParser.parse(beginDate);
        if (this.value == null) {
            throw new IllegalValueException(MESSAGE_BEGINDATE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid task begin date.
     */
    public static boolean isValidBeginDate(String test) {
        return test.matches(BEGINDATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        String beginDateString = outputFormatter.format(value);
        return beginDateString;
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BeginDate // instanceof handles nulls
                && this.value.equals(((BeginDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
