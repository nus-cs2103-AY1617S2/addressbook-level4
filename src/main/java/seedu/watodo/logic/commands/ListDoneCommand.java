package seedu.watodo.logic.commands;

//@@author A0139872R-reused
/**
 * Lists all tasks that are marked as completed in the task manager to the user.
 */
public class ListDoneCommand extends ListCommand {

    public static final String ARGUMENT = "done";

    public static final String MESSAGE_SUCCESS = "Listed all tasks that are marked as completed";

    @Override
    public CommandResult execute() {
        model.updateFilteredByTypesTaskList(ARGUMENT);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
