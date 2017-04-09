package seedu.watodo.logic.commands;

//@@author A0139872R-reused
/**
 * Lists all events in the task manager to the user.
 */
public class ListEventCommand extends ListCommand {

    public static final String ARGUMENT = "event";

    public static final String MESSAGE_SUCCESS = "Listed all events";

    @Override
    public CommandResult execute() {
        model.updateFilteredByTypesTaskList(ARGUMENT);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
