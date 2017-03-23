package seedu.taskmanager.logic.commands;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCompletedCommand extends Command {

    public static final String COMMAND_WORD = "COMPLETED";

    public static final String MESSAGE_SUCCESS = "Listed all completed tasks";

    @Override
    public CommandResult execute() {
	model.updateFilteredTaskListToShowByCompletion(true);
	return new CommandResult(MESSAGE_SUCCESS);
    }
}
