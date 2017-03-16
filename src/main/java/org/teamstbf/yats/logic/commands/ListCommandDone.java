package org.teamstbf.yats.logic.commands;

public class ListCommandDone extends ListCommand {

	public static final String MESSAGE_SUCCESS = "Listed all done tasks";

	@Override
	public CommandResult execute() {
		model.updateFilteredListToShowAll();
		return new CommandResult(MESSAGE_SUCCESS);
	}
}
