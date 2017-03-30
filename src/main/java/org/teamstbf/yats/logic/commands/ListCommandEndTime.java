package org.teamstbf.yats.logic.commands;

import java.util.Set;

//@@author A0138952W
public class ListCommandEndTime extends ListCommand {

    public static final String MESSAGE_SUCCESS = "Listed all tasks by end time";

    private final Set<String> keywords;

    public ListCommandEndTime(Set<String> keywords) {
	this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
	model.updateFilteredListToShowEndTime(keywords);
	return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredTaskList().size()));
    }
}
