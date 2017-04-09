package seedu.tache.logic.commands;

import java.util.Stack;

//@@author A0150120H
/*
 * Class to handle the Undo history. Uses a First-In-Last-Out data structure
 * Only 1 instance can be active at any point of execution
 */
public class UndoHistory {

    private Stack<Undoable> data;
    private static UndoHistory currentInstance;

    private UndoHistory() {
        data = new Stack<Undoable>();
    }

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
