package org.teamstbf.yats.logic.commands;

import org.teamstbf.yats.model.TaskManager;

/**
 * Clears the task manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "reset";
    public static final String MESSAGE_SUCCESS = "Task Manager has been reset!";

    @Override
    public CommandResult execute() {
	assert model != null;
	model.resetData(new TaskManager());
	return new CommandResult(MESSAGE_SUCCESS);
    }
}
