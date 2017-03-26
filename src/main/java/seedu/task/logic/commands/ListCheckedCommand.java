package seedu.task.logic.commands;

/**
 * Lists all completed tasks in the task manager to the user.
 */
public class ListCheckedCommand extends Command {

    public static final String LIST_COMMAND_WORD = "checked";

    public static final String MESSAGE_SUCCESS = "Listed all checked tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowChecked();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
