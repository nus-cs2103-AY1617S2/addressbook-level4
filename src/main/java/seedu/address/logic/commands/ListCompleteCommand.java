package seedu.address.logic.commands;


//@@author A0164466X
/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCompleteCommand extends Command {

    public static final String COMMAND_WORD = "lc";

    public static final String MESSAGE_SUCCESS = "Listed all completed tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowComplete();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}