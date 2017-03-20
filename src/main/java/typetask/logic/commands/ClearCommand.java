package typetask.logic.commands;

import typetask.model.TaskManager;

/**
 * Clears the TaskManager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "TaskManager has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.storeTaskManager(COMMAND_WORD);
        model.resetData(new TaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
