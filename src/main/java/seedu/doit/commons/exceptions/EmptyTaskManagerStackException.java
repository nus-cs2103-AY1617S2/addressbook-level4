package seedu.doit.commons.exceptions;

/**
 * Signals an error caused by empty task manager redo or undo stacks.
 */
public class EmptyTaskManagerStackException extends Exception {
    public EmptyTaskManagerStackException(String message) {
        super(message);
    }
}
