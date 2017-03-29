package seedu.doit.logic.commands;

//@@author A0146809W
/**
 * Lists all completed tasks in the task manager to the user.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_SUCCESS = "Listed all done tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowDone();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
