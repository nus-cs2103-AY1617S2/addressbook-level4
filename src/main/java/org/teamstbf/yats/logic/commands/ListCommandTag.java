package org.teamstbf.yats.logic.commands;

import java.util.Set;

//@@author A0138952W
public class ListCommandTag extends ListCommand {

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
