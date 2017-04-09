//@@author A0150120H
package seedu.tache.logic.commands;

import java.util.Stack;

/*
 * Class to handle the Undo history. Uses a First-In-Last-Out data structure
 * This class follows the Singleton Pattern
 */
public class UndoHistory {

    private Stack<Undoable> data;
    private static UndoHistory currentInstance;

    /**
     * Creates a new UndoHistory object.
     * This should only be called once in the entire exectution
     */
    private UndoHistory() {
        data = new Stack<Undoable>();
    }

    /**
     * Saves an Undoable Command into history
     * @param target Undoable Command to be saved
     */
    public void push(Undoable target) {
        data.push(target);
    }

    /**
     * Removes and returns the latest Undoable Command from history.
     * @return Undoable object if history is populated, null otherwise
     */
    public Undoable pop() {
        if (data.isEmpty()) {
            return null;
        } else {
            return data.pop();
        }
    }

    /**
     * Clears all history
     */
    public void clear() {
        data = new Stack<Undoable>();
    }

    /**
     * Returns an instance of this class. It is guaranteed to be the same instance throughout execution.
     * @return instance of this class
     */
    public static UndoHistory getInstance() {
        if (currentInstance == null) {
            currentInstance = new UndoHistory();
        }
        return currentInstance;
    }

}
