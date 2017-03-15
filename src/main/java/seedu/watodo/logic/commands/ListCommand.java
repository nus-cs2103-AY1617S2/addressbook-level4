package seedu.watodo.logic.commands;


/**
 * Lists all overdue tasks and upcoming tasks due the next day in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all overdue tasks and tasks due tomorrow";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
