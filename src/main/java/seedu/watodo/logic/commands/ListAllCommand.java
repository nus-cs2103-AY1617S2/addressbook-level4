package seedu.watodo.logic.commands;


/**
 * Lists all tasks in the task manager to the user.
 */
public class ListAllCommand extends ListCommand {

    public static final String ARGUMENT = "all";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
