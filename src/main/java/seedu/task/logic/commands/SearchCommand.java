package seedu.task.logic.commands;

import java.util.Set;

/**
 * Searches and lists all tasks in the task list whose name contains any of the argument keywords.
 */

public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches all tasks whose names contain any of "
            + "the specified keywords (not case-sensitive) and displays them as a list with index numbers.\n"
            + "Near-match-cases are allowed.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " birthday homework picnic";

    private final Set<String> keywords;

    public SearchCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateAdvancedFilteredTaskList(keywords);
        return new CommandResult(getMessageForTaskListShownSummary(model.getAdvancedFilteredTaskList().size()));
    }

}
