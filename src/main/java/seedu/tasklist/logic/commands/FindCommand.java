//@@author A0139221N
package seedu.tasklist.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {
    //boolean to know if the find command is for tags.
    private boolean isByTags = false;
    private boolean isByStatus = false;

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Or finds tasks which countains all the specified tags when t/ prefix is used.\n"
            + "Or finds tasks which are completed or not completed when s/ prefix is used.\n"
            + "Parameters: [KEYWORD]... [t/TAGS]...[s/STATUS]\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie\n"
            + "Example: " + COMMAND_WORD + " t/work\n"
            + "Example:" + COMMAND_WORD + " s/not completed";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    public FindCommand isByStatus() {
        isByStatus = true;
        return this;
    }

    public FindCommand isByTags() {
        isByTags = true;
        return this;
    }

    @Override
    public CommandResult execute() {
        if (isByTags) {
            model.updateFilteredTaskListTag(keywords);
        } else if (isByStatus) {
            model.updateFilteredTaskListStatus(keywords);
        } else {
            model.updateFilteredTaskList(keywords);
        }
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
