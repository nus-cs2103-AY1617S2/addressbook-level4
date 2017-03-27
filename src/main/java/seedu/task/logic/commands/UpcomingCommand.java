package seedu.task.logic.commands;

/**
 * Lists all the upcoming tasks in the task manager to the user.
 */

public class UpcomingCommand extends Command {

    public static final String COMMAND_WORD = "upcoming";

    public static final String MESSAGE_SUCCESS = "Listed all the upcoming tasks";


    @Override
    public CommandResult execute() {
        model.updateUpcomingTaskList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
