package seedu.address.logic.commands;

//@@author A0093999Y
/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCompletedCommand extends Command {

    public static final String COMMAND_WORD = "listcompleted";

    public static final String MESSAGE_SUCCESS = "Listed all completed tasks";
    public static final String MESSAGE_SUCCESS_STATUS_BAR = "Listed all completed tasks";

    @Override
    public CommandResult execute() {
        model.showCompletedTaskList();
        return new CommandResult(MESSAGE_SUCCESS, MESSAGE_SUCCESS_STATUS_BAR);
    }
}
