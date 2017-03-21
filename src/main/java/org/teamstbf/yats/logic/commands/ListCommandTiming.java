package org.teamstbf.yats.logic.commands;

import java.util.Set;

public class ListCommandTiming extends ListCommand {

	public static final String MESSAGE_SUCCESS = "Listed all tasks by timing";

	private final Set<String> keywords;

	public ListCommandTiming(Set<String> keywords) {
		this.keywords = keywords;
	}

	@Override
	public CommandResult execute() {
		model.updateFilteredListToShowStartTime(keywords);
		return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredTaskList().size()));
	}
}
