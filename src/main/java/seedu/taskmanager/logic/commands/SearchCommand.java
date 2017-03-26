package seedu.taskmanager.logic.commands;

import java.util.Set;

//@@author A0141102H
/**
 * Searches and lists all tasks in task manager whose taskname contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "SEARCH";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches all tasks who contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " eat food lunch 03/03/17";

    private final Set<String> keywords;

    public SearchCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
