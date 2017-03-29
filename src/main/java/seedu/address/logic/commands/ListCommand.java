package seedu.address.logic.commands;


/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_SUCCESS_STATUS_BAR = "Listed all tasks.";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        model.hideCompletedTaskList();
        return new CommandResult(MESSAGE_SUCCESS, MESSAGE_SUCCESS_STATUS_BAR);
    }
}
