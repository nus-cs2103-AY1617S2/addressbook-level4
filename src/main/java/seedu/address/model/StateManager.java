package seedu.address.model;

/**
 * Singleton class to handle Undo/Redo commands
 */
public class StateManager {
    private static StateManager instance = null;
    // Exists only to defeat instantiation.
    protected StateManager() {
    }
    // Returns the singleton instance
    public static StateManager getInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }
}
