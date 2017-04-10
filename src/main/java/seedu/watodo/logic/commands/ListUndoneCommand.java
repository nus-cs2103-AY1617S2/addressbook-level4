package seedu.watodo.logic.commands;

//@@author A0139872R-reused
/**
 * Lists all tasks that are not yet completed in the task manager to the user.
 */
public class ListUndoneCommand extends ListCommand {

    public static final String ARGUMENT = "undone";

    public static final String MESSAGE_SUCCESS = "Listed all tasks that are not yet completed";

    @Override
    public CommandResult execute() {
        model.updateFilteredByTypesTaskList(ARGUMENT);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
