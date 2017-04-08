package org.teamstbf.yats.logic.commands;

import java.util.Set;

//@@author A0138952W
public class ListCommandEndTime extends ListCommand {

    private final Set<String> keywords;

    public ListCommandEndTime(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        if (this.keywords.size() == 1 && this.keywords.contains("")) {
            model.updateFilteredListToShowSortedEnd();
        } else {
            model.updateFilteredListToShowEndTime(keywords);
        }
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredTaskList().size()));
    }
}
