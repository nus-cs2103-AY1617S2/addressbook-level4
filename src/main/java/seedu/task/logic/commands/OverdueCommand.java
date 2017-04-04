//@@author A0135762A
package seedu.task.logic.commands;

/**
 * Lists all overdue tasks in the task manager to the user.
 */

public class OverdueCommand extends Command {

    public static final String COMMAND_WORD = "overdue";

    public static final String MESSAGE_SUCCESS = "Listed all overdue tasks";

    @Override
    public CommandResult execute() {
        model.updateOverdueTaskList();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
