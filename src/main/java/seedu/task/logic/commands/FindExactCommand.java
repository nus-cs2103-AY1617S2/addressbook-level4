package seedu.task.logic.commands;

import java.util.Set;
//@@author A0142487Y-reused
public class FindExactCommand extends Command {

    private static final boolean isExact = true;

    public static final String COMMAND_WORD_1 = "findexact";
    public static final String COMMAND_WORD_2 = "fexact";
    public static final String COMMAND_WORD_3 = "finde";
    public static final String COMMAND_WORD_4 = "fe";

    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Finds all tasks whose names contain all of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD_1 + " do homework";

    private final Set<String> keywords;

    public FindExactCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.sortTaskList();
        model.updateFilteredTaskList(keywords, isExact);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
