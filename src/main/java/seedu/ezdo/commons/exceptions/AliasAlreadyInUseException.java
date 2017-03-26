package seedu.ezdo.commons.exceptions;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class AliasAlreadyInUseException extends Exception {

    public AliasAlreadyInUseException() {}

    public AliasAlreadyInUseException(String message) {
        super(message);
    }
}
