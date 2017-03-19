package seedu.address.model;

import java.util.Stack;

/**
 * Singleton class to handle Undo/Redo commands
 */
public class StateManager {
    
    private static StateManager instance = null;
    private Stack<String> undoStack;
    private Stack<String> redoStack;
    
    // Exists only to defeat instantiation.
    protected StateManager() {
        undoStack = new Stack<String>();
        redoStack = new Stack<String>();
    }
    // Returns the singleton instance
    public static StateManager getInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }
}
