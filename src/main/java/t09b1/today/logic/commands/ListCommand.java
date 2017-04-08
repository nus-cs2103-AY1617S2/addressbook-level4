package t09b1.today.logic.commands;

/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_SUCCESS_STATUS_BAR = MESSAGE_SUCCESS;

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        model.indicateTaskManagerChanged(model.MESSAGE_ON_UPDATE);
        model.hideCompletedTaskList();
        return new CommandResult(MESSAGE_SUCCESS, MESSAGE_SUCCESS_STATUS_BAR);
    }
}
