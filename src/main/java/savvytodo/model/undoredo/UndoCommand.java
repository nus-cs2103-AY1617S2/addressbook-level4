package savvytodo.model.undoredo;

import savvytodo.logic.commands.exceptions.CommandException;
import savvytodo.model.TaskManager;

//@@A0124863A
/**
 * @author A0124863A
 * Represents undo command with hidden internal logic and the ability to be executed.
 */
public abstract class UndoCommand {
    protected TaskManager taskManager;

    /**
     * Executes the command
     */
    public abstract void execute() throws CommandException;

    /**
     * @return a redo operation that does the opposite of an undo operation just performed
     */
    public abstract UndoCommand reverseUndo();

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

}
