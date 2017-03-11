package seedu.typed.commons.exceptions;

/**
 * Signals an error caused by duplicate data where there should be none.
 */
@SuppressWarnings("serial")
public abstract class DuplicateDataException extends IllegalValueException {
    public DuplicateDataException(String message) {
        super(message);
    }
}
