package seedu.today.logic.commands;

//@@author A0093999Y
/**
 * Opens the completed task list in the task manager for the user.
 */
public class ListCompletedCommand extends Command {

    public static final String COMMAND_WORD = "listcompleted";

    public static final String MESSAGE_SUCCESS = "Listed all completed tasks";
    public static final String MESSAGE_SUCCESS_STATUS_BAR = MESSAGE_SUCCESS;

    @Override
    public CommandResult execute() {
        model.showCompletedTaskList();
        return new CommandResult(MESSAGE_SUCCESS, MESSAGE_SUCCESS_STATUS_BAR);
    }
}
