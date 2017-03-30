package seedu.address.model.task;

import java.util.StringTokenizer;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.recurrenceparser.RecurrenceManager;
import seedu.address.logic.recurrenceparser.RecurrenceParser;

public class Recurrence {

    public static final String MESSAGE_RECURRENCE_CONSTRAINTS =
            "A recurrence should contain a number followed by a word";
    private static final RecurrenceParser intervalParser = new RecurrenceManager();

    /*
     * The format should be one number followed by a word in lowercase
     */
    public static final String RECURRENCE_VALIDATION_REGEX = "[\\p{Digit}]+ [\\p{Lower}]+";

    public final int value;
    public final int interval;
    public final String intervalString;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Recurrence(String recurrence) throws IllegalValueException {
        assert recurrence != null;
        String trimmedRecurrence = recurrence.trim();
        if (isValidRecurrence(trimmedRecurrence)) {
            StringTokenizer st = new StringTokenizer(trimmedRecurrence);
            this.value = Integer.parseInt(st.nextToken());

            this.intervalString = st.nextToken();
            this.interval = intervalParser.getInterval(intervalString);
        } else {
            throw new IllegalValueException(MESSAGE_RECURRENCE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid  name.
     */
    public static boolean isValidRecurrence(String test) {
        return test.matches(RECURRENCE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value + " " + intervalString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Recurrence // instance of handles nulls
                && (this.interval == ((Recurrence) other).interval)
                && (this.value == ((Recurrence) other).value));
    }

}
