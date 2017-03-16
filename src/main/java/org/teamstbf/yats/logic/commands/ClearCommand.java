package org.teamstbf.yats.logic.commands;

import org.teamstbf.yats.model.TaskManager;

/**
 * Clears the address book.
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
}
