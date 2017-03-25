package seedu.ezdo.commons.exceptions;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class InvalidSortingOrderException extends Exception {

    public InvalidSortingOrderException() {}

    public InvalidSortingOrderException(String message) {
        super(message);
    }

}
