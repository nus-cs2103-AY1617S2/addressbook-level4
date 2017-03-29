package seedu.watodo.logic.commands;

//@@author A0139872R-reused
/**
 * Lists all floating tasks in the task manager to the user.
 */
public class ListFloatCommand extends ListCommand {

    public static final String COMMAND_WORD = "float";

    public static final String MESSAGE_SUCCESS = "Listed all floating tasks";

    @Override
    public CommandResult execute() {
        model.updateFilteredByTypesTaskList(COMMAND_WORD);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
