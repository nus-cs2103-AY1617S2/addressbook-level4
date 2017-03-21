package seedu.doit.model;

import java.util.Stack;
import java.util.logging.Logger;

import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.exceptions.EmptyTaskManagerStackException;

public class TaskManagerStack {
    public static final String NOTHING_TO_REDO = "There is nothing to redo!";
    public static final String NOTHING_TO_UNDO = "There is nothing to undo!";
    private static Stack<ReadOnlyItemManager> undoStack;
    private static Stack<ReadOnlyItemManager> redoStack;
    private static final Logger logger = LogsCenter.getLogger(TaskManagerStack.class);
    private static TaskManagerStack instance = null;
    private static final int STACK_SIZE = 10;

    protected TaskManagerStack() {
        undoStack = new Stack<ReadOnlyItemManager>();
        redoStack = new Stack<ReadOnlyItemManager>();
    }

    public static TaskManagerStack getInstance() {
        if (instance == null) {
            instance = new TaskManagerStack();
        }
        return instance;
    }

    public static void addToUndoStack(ReadOnlyItemManager readOnlyItemManager) {
        undoStack.push(readOnlyItemManager);
    }

    /**
     * When there is a need to undo a command this is called
     *
     * @return the undid task manager
     * @throws EmptyTaskManagerStackException
     *             if there is an empty undostack
     */
    public ReadOnlyItemManager loadOlderTaskManager() throws EmptyTaskManagerStackException {
        if (undoStack.isEmpty()) {
            logger.info(NOTHING_TO_UNDO);
            throw new EmptyTaskManagerStackException(NOTHING_TO_UNDO);
        }
        ReadOnlyItemManager undidTaskManager = undoStack.pop();
        redoStack.push(undidTaskManager);
        return undidTaskManager;
    }

    /**
     * When there is a need to redo a command this is called
     *
     * @return the redid task manager
     * @throws EmptyTaskManagerStackException
     *             if there is an empty redostack
     */
    public ReadOnlyItemManager loadNewerTaskManager() throws EmptyTaskManagerStackException {
        if (redoStack.isEmpty()) {
            logger.info(NOTHING_TO_REDO);
            throw new EmptyTaskManagerStackException(NOTHING_TO_REDO);
        }
        ReadOnlyItemManager redidTaskManager = redoStack.pop();
        undoStack.push(redidTaskManager);
        return redidTaskManager;
    }

}
