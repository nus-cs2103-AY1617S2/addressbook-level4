package savvytodo.model.undoredo;

import savvytodo.model.ReadOnlyTaskManager;
import savvytodo.model.TaskManager;

//@@A0124863A
/**
 * @author A0124863A
 * Undo an clear operation by restoring the original
 */

public class UndoClearCommand extends UndoCommand {
    private TaskManager currTaskManager;
    private TaskManager newTaskManager;
    public UndoClearCommand(ReadOnlyTaskManager currTaskManager, ReadOnlyTaskManager newTaskManager) {
        this.currTaskManager = new TaskManager(currTaskManager);
        this.newTaskManager = new TaskManager(newTaskManager);
    }


    @Override
    public void execute() {
        assert taskManager != null;
        taskManager.resetData(currTaskManager);
    }

    @Override
    public UndoCommand reverseUndo() {
        return new UndoClearCommand(newTaskManager, currTaskManager);
    }

}
