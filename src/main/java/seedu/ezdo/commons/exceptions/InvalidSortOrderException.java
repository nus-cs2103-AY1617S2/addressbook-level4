package seedu.ezdo.commons.exceptions;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class InvalidSortOrderException extends Exception {

    public InvalidSortOrderException() {}

    public InvalidSortOrderException(String message) {
        super(message);
    }

}
