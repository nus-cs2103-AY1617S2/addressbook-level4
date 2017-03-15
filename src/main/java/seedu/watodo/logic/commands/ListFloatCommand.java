package seedu.watodo.logic.commands;


/**
 * Lists all floating tasks in the task manager to the user.
 */
public class ListFloatCommand extends ListCommand {

    public static final String COMMAND_WORD = "float";

    public static final String MESSAGE_SUCCESS = "Listed all floating tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
