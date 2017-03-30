package org.teamstbf.yats.logic.commands;

import java.util.Set;

//@@author A0138952W
public class ListCommandDone extends ListCommand {

    public static final String MESSAGE_SUCCESS = "Listed all done tasks";

    private final Set<String> keywords;

    public ListCommandDone(Set<String> keywords) {
	this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
	model.updateFilteredListToShowDone(keywords);
	return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredTaskList().size()));
    }
}
