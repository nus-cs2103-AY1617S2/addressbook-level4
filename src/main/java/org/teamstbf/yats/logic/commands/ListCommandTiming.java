package org.teamstbf.yats.logic.commands;

public class ListCommandTiming extends ListCommand {

	public static final String MESSAGE_SUCCESS = "Listed all tasks by timing";

	@Override
	public CommandResult execute() {
		model.updateFilteredListToShowAll();
		return new CommandResult(MESSAGE_SUCCESS);
	}
}
