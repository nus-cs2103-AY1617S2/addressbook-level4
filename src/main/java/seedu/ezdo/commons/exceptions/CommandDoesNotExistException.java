package seedu.ezdo.commons.exceptions;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class CommandDoesNotExistException extends Exception {

    public CommandDoesNotExistException() {}

    public CommandDoesNotExistException(String message) {
        super(message);
    }

}
