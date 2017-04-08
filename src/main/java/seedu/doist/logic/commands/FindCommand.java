package seedu.doist.logic.commands;

import seedu.doist.model.task.ReadOnlyTask.ReadOnlyTaskMatchingComparator;

/**
 * Finds and lists all tasks in to-do list whose description contains any of the
 * argument keywords. Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String DEFAULT_COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = DEFAULT_COMMAND_WORD
            + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n" + "Example: " + DEFAULT_COMMAND_WORD + " go NUS";

    private final String keywords;

    public FindCommand(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords);
        model.sortTasks(new ReadOnlyTaskMatchingComparator(keywords));
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
