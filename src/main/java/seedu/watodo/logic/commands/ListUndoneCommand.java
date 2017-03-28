package seedu.watodo.logic.commands;


/**
 * Lists all tasks that are not yet completed in the task manager to the user.
 */
public class ListUndoneCommand extends ListCommand {

    public static final String COMMAND_WORD = "undone";

    public static final String MESSAGE_SUCCESS = "Listed all tasks that are not yet completed";

    @Override
    public CommandResult execute() {
        model.updateFilteredByTypesTaskList(COMMAND_WORD);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
