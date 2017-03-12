package org.teamstbf.yats.logic.commands;

public class ListCommandTitle extends ListCommand {
	
	@Override
	public CommandResult execute() {
		model.updateFilteredListToShowAll();
		return new CommandResult(MESSAGE_SUCCESS);
	}
}
