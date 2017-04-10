package org.teamstbf.yats.logic.commands;

import java.util.Set;

//@@author A0138952W
public class ListCommandStartTime extends ListCommand {

    private final Set<String> keywords;

    public ListCommandStartTime(Set<String> keywords) {
        this.keywords = keywords;
        System.out.println(keywords.size());
    }

    @Override
    public CommandResult execute() {
        if (this.keywords.size() == 1 && this.keywords.contains("")) {
            model.updateFilteredListToShowSortedStart();
        } else {
            model.updateFilteredListToShowStartTime(keywords);
        }
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredTaskList().size()));
    }
}
