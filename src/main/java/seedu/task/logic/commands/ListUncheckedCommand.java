package seedu.task.logic.commands;

/**
 * Lists all incomplete tasks in the task manager to the user.
 */
public class ListUncheckedCommand extends Command {

    public static final String LIST_COMMAND_WORD = "unchecked";

    public static final String MESSAGE_SUCCESS = "Listed all unchecked tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowUnchecked();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
