package seedu.jobs.logic.commands;

import java.util.Set;

//@@author A0164440M
/**
 * list all tasks with or without arguments
 */

public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": list all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Set<String> keywords;

    public ListCommand() {
        this.keywords = null;
    }

    public ListCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    //@@author A0164440M
    @Override
    public CommandResult execute() {
        if (keywords == null) {
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            model.updateFilteredListToShowAll(keywords);
        }
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
    //@@author

}
