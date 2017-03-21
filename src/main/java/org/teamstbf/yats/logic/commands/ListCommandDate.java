package org.teamstbf.yats.logic.commands;

public class ListCommandDate extends ListCommand {

	public static final String MESSAGE_SUCCESS = "Listed all tasks by deadline";

	@Override
	public CommandResult execute() {
		model.updateFilteredListToShowAll();
		return new CommandResult(MESSAGE_SUCCESS);
	}
}
