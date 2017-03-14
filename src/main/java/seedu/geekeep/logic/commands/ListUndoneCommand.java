package seedu.geekeep.logic.commands;

/**
 * Lists all uncompleted tasks in the task manager to the user.
 */
public class ListUndoneCommand extends Command {

    public static final String COMMAND_WORD = "listundone";

    public static final String MESSAGE_SUCCESS = "Listed all uncompleted tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredTaskListToShowUndone();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}