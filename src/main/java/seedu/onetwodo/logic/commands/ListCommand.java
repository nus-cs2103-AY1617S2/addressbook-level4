package seedu.onetwodo.logic.commands;

import seedu.onetwodo.model.DoneStatus;

/**
 * Lists all tasks in the todo list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed tasks";

    private DoneStatus doneStatus;

    public ListCommand(String parameter) {
        assert parameter != null;
        switch (parameter) {
        case "DONE": // view done tasks
            doneStatus = DoneStatus.DONE;
            break;
        case "UNDONE":
            doneStatus = DoneStatus.UNDONE;
            break;
        case "ALL": // view all tasks
            doneStatus = DoneStatus.ALL;
            break;
        }
    }

    @Override
    public CommandResult execute() {
        switch (doneStatus) {
        case ALL:
            model.setDoneStatus(DoneStatus.ALL);
            break;
        case DONE:
            model.setDoneStatus(DoneStatus.DONE);
            break;
        case UNDONE:
        default:
            model.setDoneStatus(DoneStatus.UNDONE);
        }
        model.resetSearchStrings();
        model.updateByDoneStatus();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
