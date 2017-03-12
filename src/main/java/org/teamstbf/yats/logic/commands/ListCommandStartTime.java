package org.teamstbf.yats.logic.commands;

public class ListCommandStartTime extends ListCommand {
	
	public static final String MESSAGE_SUCCESS = "Listed all tasks by start time";
	
	@Override
	public CommandResult execute() {
		model.updateFilteredListToShowAll();
		return new CommandResult(MESSAGE_SUCCESS);
	}
}
