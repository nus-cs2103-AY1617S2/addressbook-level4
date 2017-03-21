package org.teamstbf.yats.logic.commands;

import java.util.Set;

public class ListCommandTag extends ListCommand {

	public static final String MESSAGE_SUCCESS = "Listed all tasks by tags";

	private final Set<String> keywords;

	public ListCommandTag(Set<String> keywords) {
		this.keywords = keywords;
	}

	@Override
	public CommandResult execute() {
		model.updateFilteredListToShowTags(keywords);
		return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredTaskList().size()));
	}
}
