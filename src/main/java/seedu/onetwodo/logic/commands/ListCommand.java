package seedu.onetwodo.logic.commands;

import seedu.onetwodo.logic.parser.DoneStatus;

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
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS);
        case DONE: // view done
            model.setDoneStatus(DoneStatus.DONE);
            model.updateFilteredDoneTaskList();
            return new CommandResult(MESSAGE_SUCCESS);
        case UNDONE:
            model.setDoneStatus(DoneStatus.UNDONE);
            model.updateFilteredUndoneTaskList();
            return new CommandResult(MESSAGE_SUCCESS);
        default:
            model.setDoneStatus(DoneStatus.UNDONE);
            model.updateFilteredUndoneTaskList();
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
}
