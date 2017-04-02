//@@author A0139539R
package seedu.address.logic.commands;

import java.util.Date;
import java.util.Set

;

import seedu.address.model.tag.UniqueTagList;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose title contain any of "
            + "the specified keywords (minimally 3-letter substrings) and displays them as a list with index numbers.\n"
            + "Parameters: find [KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " groceries or groc";

    private Set<String> keywords;
    private UniqueTagList tagList;
    private Date date;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    public FindCommand(UniqueTagList tagList) {
        this.tagList = tagList;
    }

    public FindCommand(Date date) {
        this.date = date;
    }

    @Override
    public CommandResult execute() {
        if (tagList != null) {
            model.updateFilteredTaskListToShowFilteredTasks(tagList);
        } else if (date != null) {
            model.updateFilteredTaskListToShowFilteredTasks(date);
        } else {
            model.updateFilteredTaskListToShowFilteredTasks(keywords);
        }
        return new CommandResult(getMessageForTaskListShownSummary(
                model.getNonFloatingTaskList().size()
                + model.getFloatingTaskList().size()
                + model.getCompletedTaskList().size()
                ));
    }

}
