package seedu.task.logic.commands;

/**
 * Lists all tasks in the task manager to the user.
 * Tasks are sorted according to the date (if there's any).
 */

public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
