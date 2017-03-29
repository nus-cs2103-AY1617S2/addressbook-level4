package seedu.address.commons.exceptions;

//@@author A0162877N
/**
 * Signals that the given date cannot be parse to DateTimeParserManager.
 */
public class IllegalDateTimeValueException extends Exception {

    public static final String MESSAGE_DEADLINE_ERROR_PARSING = "Deadline provided is in the wrong format!";

    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public IllegalDateTimeValueException() {
        super(MESSAGE_DEADLINE_ERROR_PARSING);
    }
}
