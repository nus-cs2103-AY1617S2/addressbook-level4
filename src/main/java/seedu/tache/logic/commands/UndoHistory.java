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

    /*
     * Creates a new UndoHistory object.
     * This should only be called once in the entire exectution
     */
    private UndoHistory() {
        data = new Stack<Undoable>();
    }

    /*
     * Adds an Undoable Command onto the stack
     * @param
     */
    public void push(Undoable target) {
        data.push(target);
    }

    public Undoable pop() {
        if (data.isEmpty()) {
            return null;
        } else {
            return data.pop();
        }
    }

    public void clear() {
        data = new Stack<Undoable>();
    }

    public static UndoHistory getInstance() {
        if (currentInstance == null) {
            currentInstance = new UndoHistory();
        }
        return currentInstance;
    }

}
