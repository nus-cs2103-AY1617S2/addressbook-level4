package org.teamstbf.yats.logic.commands;

public class ListCommandTitle extends ListCommand {

	public static final String MESSAGE_SUCCESS = "Listed all tasks by title";

	@Override
	public CommandResult execute() {
		model.sortFilteredEventList();
		return new CommandResult(MESSAGE_SUCCESS);
	}
}
