package seedu.ezdo.logic.commands;

import seedu.ezdo.logic.commands.exceptions.CommandException;
import seedu.ezdo.model.todo.UniqueTaskList.SortCriteria;

/**
 * Sorts all tasks in ezDo by their name, start date, due date or priority.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String SHORT_COMMAND_WORD = "s";

    public static final String MESSAGE_SUCCESS = "Sorted all tasks";
    public static final String MESSAGE_FAILURE = "You must specify a valid field to sort by.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Sorts the task by the field specified.\n"
        + "Parameters: FIELD (must be either \"name\", \"priority\", \"start date\" or \"due date\")\n"
        + "Example: " + COMMAND_WORD + " due date";

    private SortCriteria sortCriteria;

    public SortCommand(SortCriteria sortCriteria) {
        this.sortCriteria = sortCriteria;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (sortCriteria == null) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        model.sortTasks(sortCriteria);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
