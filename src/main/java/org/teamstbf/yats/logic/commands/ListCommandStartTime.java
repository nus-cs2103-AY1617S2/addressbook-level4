package org.teamstbf.yats.logic.commands;

import java.util.Set;

//@@author A0138952W
public class ListCommandStartTime extends ListCommand {

	public static final String MESSAGE_SUCCESS = "Listed all tasks by start time";

	private final Set<String> keywords;

	public ListCommandStartTime(Set<String> keywords) {
		this.keywords = keywords;
	}

	@Override
	public CommandResult execute() {
		model.updateFilteredListToShowStartTime(keywords);
		return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredTaskList().size()));
	}
}
