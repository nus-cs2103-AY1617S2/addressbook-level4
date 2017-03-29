package seedu.watodo.logic.commands;

import java.util.Set;

//@@author A0139872R-reused
/**
 * Lists all tasks with the entered tag in the task manager to the user.
 */
public class ListTagCommand extends ListCommand {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks with the entered tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "#TAG : Lists all tasks whose tags contain any of "
        + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
        + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + " #budget";

    private final Set<String> keywords;

    public ListTagCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredByTagsTaskList(keywords);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
