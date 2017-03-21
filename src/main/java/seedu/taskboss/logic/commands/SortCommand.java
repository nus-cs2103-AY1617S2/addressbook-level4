package seedu.taskboss.logic.commands;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.logic.commands.exceptions.InvalidDatesException;
import seedu.taskboss.model.task.UniqueTaskList.SortBy;

//@@author A0143157J
/**
 * Sort tasks by start date, end date or priority.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_WORD_SHORT = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_SHORT
            + ": Sorts all tasks by either start date, end date or priority. "
            + "Parameters: SORT_TYPE (sd or ed or p)\n"
            + "Example: " + COMMAND_WORD + " sd\n"
            + "Example: " + COMMAND_WORD_SHORT + " ed";

    public static final String MESSAGE_SUCCESS = "Sorted all tasks.";
    public static final String ERROR_INVALID_SORT_TYPE = "Invalid sort type.";

    private SortBy sortType;

    public SortCommand(SortBy sortType) {
        this.sortType = sortType;
    }

    @Override
    public CommandResult execute() throws CommandException, IllegalValueException, InvalidDatesException {
        assert model != null;
        try {
            model.sortTasks(sortType);
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (IllegalValueException ive) {
            throw new CommandException(ERROR_INVALID_SORT_TYPE);
        }
    }

}
