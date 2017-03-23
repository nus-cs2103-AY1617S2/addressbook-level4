package seedu.onetwodo.commons.exceptions;

//@@author A0135739W
/**
 * Signals that Undo Command is trying to access an empty history.
 */
public class EmptyHistoryException extends Exception {
    /**
     * @param message indicating that history is empty.
     */
    public EmptyHistoryException(String message) {
        super(message);
    }
}
