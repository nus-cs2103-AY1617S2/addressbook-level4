package seedu.watodo.logic.commands;


/**
 * Lists all marked tasks in the task manager to the user.
 */
public class ListMarkedCommand extends ListCommand {

    public static final String COMMAND_WORD = "marked";

    public static final String MESSAGE_SUCCESS = "Listed all marked tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
