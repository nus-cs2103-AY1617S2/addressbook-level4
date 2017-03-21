package org.teamstbf.yats.logic.commands;

public class ListCommandLocation extends ListCommand {

	public static final String MESSAGE_SUCCESS = "Listed all tasks by location";

	@Override
	public CommandResult execute() {
		model.updateFilteredListToShowAll();
		return new CommandResult(MESSAGE_SUCCESS);
	}
}
