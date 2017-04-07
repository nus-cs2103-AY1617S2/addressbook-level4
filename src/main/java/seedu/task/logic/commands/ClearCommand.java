package seedu.task.logic.commands;

import seedu.task.model.TaskManager;

/**
 * Clears the task manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task Manager has been cleared!";

    @Override
    public CommandResult execute() {
	assert model != null;
	model.resetData(new TaskManager());
	return new CommandResult(MESSAGE_SUCCESS);
    }

    public boolean isUndoable() {
	return true;
    }
}
