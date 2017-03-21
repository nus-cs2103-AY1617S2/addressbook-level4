package seedu.task.logic.commands;


/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks with index numbers, "
            + "use checked or unchecked as keyword to only show checked or unchecked tasks\n"
            + "Parameters: [" + ListUncheckedCommand.LIST_COMMAND_WORD + "][unchecked] \n"
            + "Example: " + COMMAND_WORD + " " + ListUncheckedCommand.LIST_COMMAND_WORD;

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
