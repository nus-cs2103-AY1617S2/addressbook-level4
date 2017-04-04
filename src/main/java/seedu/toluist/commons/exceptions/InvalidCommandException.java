package seedu.toluist.commons.exceptions;

/**
 * Signals that some given command does not fulfill some constraints.
 */
public class InvalidCommandException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public InvalidCommandException(String message) {
        super(message);
    }
}
