package seedu.doit.model;

import java.util.Stack;
import java.util.logging.Logger;

import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.exceptions.EmptyTaskManagerStackException;

//@@author A0138909R
public class TaskManagerStack {
    public static final String NOTHING_TO_REDO = "There is nothing to redo!";
    public static final String NOTHING_TO_UNDO = "There is nothing to undo!";
    private static final Stack<ReadOnlyTaskManager> undoStack = new Stack<ReadOnlyTaskManager>();
    private static final Stack<ReadOnlyTaskManager> redoStack = new Stack<ReadOnlyTaskManager>();
    private static final Logger logger = LogsCenter.getLogger(TaskManagerStack.class);
    private static TaskManagerStack instance = null;

    protected TaskManagerStack() {
    }

    public static TaskManagerStack getInstance() {
        if (instance == null) {
            instance = new TaskManagerStack();
        }
        return instance;
    }

    /**
     * Adds another copy of the ReadOnlyTaskManager to the undostack
     *
     * @param readOnlyTaskManager
     */
    public void addToUndoStack(ReadOnlyTaskManager readOnlyTaskManager) {
        ReadOnlyTaskManager oldReadOnlyTaskManager = new TaskManager(readOnlyTaskManager);
        undoStack.push(oldReadOnlyTaskManager);
    }

    /**
     * When there is a need to undo a command this is called
     *
     * @return the undid task manager
     * @throws EmptyTaskManagerStackException
     *             if there is an empty undostack
     */
    public ReadOnlyTaskManager loadOlderTaskManager(ReadOnlyTaskManager readOnlyTaskManager)
            throws EmptyTaskManagerStackException {
        if (undoStack.isEmpty()) {
            logger.info(NOTHING_TO_UNDO);
            throw new EmptyTaskManagerStackException(NOTHING_TO_UNDO);
        }
        ReadOnlyTaskManager newReadOnlyTaskManager = new TaskManager(readOnlyTaskManager);
        redoStack.push(newReadOnlyTaskManager);
        ReadOnlyTaskManager undidTaskManager = undoStack.pop();
        return undidTaskManager;
    }

    /**
     * When there is a need to redo a command this is called
     *
     * @return the redid task manager
     * @throws EmptyTaskManagerStackException
     *             if there is an empty redostack
     */
    public ReadOnlyTaskManager loadNewerTaskManager(ReadOnlyTaskManager readOnlyTaskManager)
            throws EmptyTaskManagerStackException {
        if (redoStack.isEmpty()) {
            logger.info(NOTHING_TO_REDO);
            throw new EmptyTaskManagerStackException(NOTHING_TO_REDO);
        }
        ReadOnlyTaskManager oldReadOnlyTaskManager = new TaskManager(readOnlyTaskManager);
        undoStack.push(oldReadOnlyTaskManager);
        ReadOnlyTaskManager redidTaskManager = redoStack.pop();
        return redidTaskManager;
    }

    /**
     * Clears redo stack
     */
    public void clearRedoStack() {
        redoStack.clear();
    }

    /**
     * Clears undo stack
     */
    public void clearUndoStack() {
        undoStack.clear();
    }

}
