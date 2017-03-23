package seedu.doit.commons.exceptions;

//@@author A0138909R
/**
 * Signals an error caused by empty task manager redo or undo stacks.
 */
public class EmptyTaskManagerStackException extends Exception {
    public EmptyTaskManagerStackException(String message) {
        super(message);
    }
}
