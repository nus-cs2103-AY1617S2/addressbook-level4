package seedu.geekeep.commons.exceptions;

/**
 * Signals that some given data does not fulfill calendar constraints.
 */
public class InvalidDateTimeException extends Exception {
    public InvalidDateTimeException() {
        super("Invalid DateTime");
    }
}
