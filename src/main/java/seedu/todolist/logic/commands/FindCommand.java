package seedu.todolist.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Set<String> keywords;
    private String commandText;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords);
        commandText = getMessageForTaskListShownSummary(model.getFilteredTaskList().size());
        return new CommandResult(commandText);
    }

    @Override
    public boolean isMutating() {
        return false;
    }

    @Override
    public String getCommandText() {
        return commandText;
    }
}
