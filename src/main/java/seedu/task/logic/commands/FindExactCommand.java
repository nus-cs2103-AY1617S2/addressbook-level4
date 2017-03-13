package seedu.task.logic.commands;

import java.util.Set;

public class FindExactCommand extends Command {

    private static final boolean isExact = true;

    public static final String COMMAND_WORD = "findexact";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain all of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " do homework";

    private final Set<String> keywords;

    public FindExactCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords, isExact);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
