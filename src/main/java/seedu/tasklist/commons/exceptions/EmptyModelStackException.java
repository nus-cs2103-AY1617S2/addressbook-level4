package seedu.tasklist.commons.exceptions;
/**
 * Signals there is no more available states to undo/redo.
 *
 */
public class EmptyModelStackException extends Exception {
    public EmptyModelStackException(String message) {
        super(message);
    }
}
