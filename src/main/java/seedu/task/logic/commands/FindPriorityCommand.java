package seedu.task.logic.commands;

/**
 * Lists all the high priority tasks in the task manager to the user.
 */

public class FindPriorityCommand extends Command {

    public static final String COMMAND_WORD = "high";

    public static final String MESSAGE_SUCCESS = "Listed all the high priority tasks";


    @Override
    public CommandResult execute() {
        model.updatePriorityTaskList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
