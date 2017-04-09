package org.teamstbf.yats.logic.commands;

import org.teamstbf.yats.model.TaskManager;

/**
 * Clears the task manager.
 */
public class ResetCommand extends Command {

	public static final String COMMAND_WORD = "reset";
	public static final String MESSAGE_SUCCESS = "Task Manager has been reset!";

	@Override
	public CommandResult execute() {
		assert model != null;
		model.saveImageOfCurrentTaskManager(); // For undo command
		model.resetData(new TaskManager());
		return new CommandResult(MESSAGE_SUCCESS);
	}
}
