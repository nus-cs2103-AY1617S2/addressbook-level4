package seedu.task.logic.commands;

//@@author A0135762A
/**
 * Lists all the high priority tasks in the task manager to the user.
 */

public class FindPriorityCommand extends Command {

    public static final String COMMAND_WORD = "important";

    public static final String MESSAGE_SUCCESS = "Listed all the high priority tasks!";


    @Override
    public CommandResult execute() {
        model.updatePriorityTaskList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
