package seedu.opus.logic.commands;

import java.util.List;

import seedu.opus.model.qualifier.Qualifier;

/**
 * Finds and lists all tasks in task manager whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds by keywords or attributes and displays them as a list with index numbers.\n"
            + "At least one of the parameters below must be specified.\n"
            + "Parameters: [KEYWORDS]... [PRIORITY] [STARTTIME] [ENDTIME]\n"
            + "Example: " + COMMAND_WORD + " do homework b/hi";

    private final List<Qualifier> qualifiers;

    public FindCommand(List<Qualifier> qualifiers) {
        this.qualifiers = qualifiers;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(qualifiers);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
