package savvytodo.model.undoredo;

import savvytodo.logic.commands.exceptions.CommandException;
import savvytodo.model.task.ReadOnlyTask;
import savvytodo.model.task.Task;
import savvytodo.model.task.UniqueTaskList.DuplicateTaskException;

//@@A0124863A
/**
 * @author A0124863A
 * Undo an edit operation by restoring the edited task to its original
 */
public class UndoEditCommand extends UndoCommand {
    private Task undoTask;
    private Task redoTask;
    private int index;

    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";


    public UndoEditCommand(int index, ReadOnlyTask undoTask, ReadOnlyTask redoTask) {
        this.undoTask = new Task(undoTask);
        this.redoTask = new Task(redoTask);
        this.index = index;

    }

    @Override
    public void execute() throws CommandException {
        assert taskManager != null;
        try {
            taskManager.updateTask(index, undoTask);
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    @Override
    public UndoCommand reverseUndo() {
        return new UndoEditCommand(index, redoTask, undoTask);
    }



}
