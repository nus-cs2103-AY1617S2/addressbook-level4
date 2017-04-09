package seedu.watodo.logic.commands;

//@@author A0139872R-reused
/**
 * Lists all tasks with deadlines in the task manager to the user.
 */
public class ListDeadlineCommand extends ListCommand {

    public static final String ARGUMENT = "deadline";

    public static final String MESSAGE_SUCCESS = "Listed all tasks with deadlines";

    @Override
    public CommandResult execute() {
        model.updateFilteredByTypesTaskList(ARGUMENT);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
