package seedu.address.logic.commands;

import seedu.address.logic.LogicManager;
import seedu.address.model.TaskManager;

/**
 * Clears the task manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task manager has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        saveCurrentState();
        model.resetData(new TaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Save the data in task manager if command is mutating the data
     */
    public void saveCurrentState() {
        if (isMutating()) {
            try {
                LogicManager.undoCommandHistory.addStorageHistory(model.getTaskManager().getImmutableTaskList(),
                        model.getTaskManager().getImmutableLabelList());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean isMutating() {
        return true;
    }
}
