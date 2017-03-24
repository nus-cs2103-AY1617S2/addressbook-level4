package savvytodo.model.undoredo;

import savvytodo.logic.commands.exceptions.CommandException;
import savvytodo.model.task.Task;
import savvytodo.model.task.UniqueTaskList.TaskNotFoundException;

//@@A0124863A
/**
 * @author A0124863A
 * Undo an add operation by deleting the added task
 */
public class UndoAddCommand extends UndoCommand {
    private Task task;

    public UndoAddCommand(Task task) {
        this.task = task;
    }
    @Override
    public void execute() throws CommandException {
        assert taskManager != null;
        try {
            taskManager.removeTask(task);
        } catch (TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        }
    }

    @Override
    public UndoCommand reverseUndo() {
        return new UndoDeleteCommand(task);
    }



}
