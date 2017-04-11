package seedu.opus.model;

import java.util.Stack;

import seedu.opus.model.util.InvalidUndoException;

//@@author A0148087W
/**
 * Memento Collector for TaskManager States and return the appropriate states for undo/redo operations
 *
 */
public class TaskManagerStateHistory {
    private Stack<TaskManager> undoStack;
    private Stack<TaskManager> redoStack;

    public static final String MESSAGE_INVALID_REDO = "No available action to redo!";
    public static final String MESSAGE_INVALID_UNDO = "No available action to undo!";

    public TaskManagerStateHistory() {
        undoStack = new Stack<TaskManager>();
        redoStack = new Stack<TaskManager>();
    }

    /**
     * Backup source taskManager to Undo History and clear all Redo data
     * @param source
     */
    public void backupCurrentState(TaskManager source) {
        assert source != null;
        TaskManager backup = new TaskManager(source);
        this.undoStack.push(backup);
        this.redoStack.clear();
    }

    /**
     * Retrieve the previous TaskManager state for undo operation.
     * Pushes a copy of current state into the Redo stack.
     * @param currentState
     * @return previous TaskManager state
     * @throws InvalidUndoException
     */
    public TaskManager getPreviousState(TaskManager currentState) throws InvalidUndoException {
        if (this.undoStack.isEmpty()) {
            throw new InvalidUndoException(MESSAGE_INVALID_UNDO);
        }
        TaskManager prevState = this.undoStack.pop();
        this.redoStack.push(new TaskManager(currentState));
        return prevState;
    }

    /**
     * Retrieve the next TaskManager state that reverts the most recent undo action.
     * Pushes a copy of current state into the Undo History.
     * @param currentState
     * @return next TaskManager state
     * @throws InvalidUndoException
     */
    public TaskManager getNextState(TaskManager currentState) throws InvalidUndoException {
        if (this.redoStack.isEmpty()) {
            throw new InvalidUndoException(MESSAGE_INVALID_REDO);
        }
        TaskManager nextState = this.redoStack.pop();
        this.undoStack.push(new TaskManager(currentState));
        return nextState;
    }
}
