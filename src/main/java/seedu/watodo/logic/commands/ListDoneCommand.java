package seedu.watodo.logic.commands;


/**
 * Lists all tasks that are completed in the task manager to the user.
 */
public class ListDoneCommand extends ListCommand {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_SUCCESS = "Listed all tasks that are completed";

    @Override
    public CommandResult execute() {
        model.updateFilteredByTypesTaskList(COMMAND_WORD);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
