package seedu.address.logic.commands;


//@@author A0164466X
/**
 * Lists all tasks in the task manager to the user.
 */
public class ListIncompleteCommand extends Command {

    public static final String COMMAND_WORD = "li";

    public static final String MESSAGE_SUCCESS = "Listed all incomplete tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowIncomplete();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}