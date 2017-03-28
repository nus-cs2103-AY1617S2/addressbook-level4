package seedu.taskboss.logic.commands;

import seedu.taskboss.commons.core.EventsCenter;
import seedu.taskboss.commons.events.ui.JumpToListRequestEvent;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.logic.commands.exceptions.InvalidDatesException;
import seedu.taskboss.model.task.UniqueTaskList.SortBy;

//@@author A0143157J
/**
 * Sort tasks by start date, end date or priority.
 */
public class SortCommand extends Command {

    private static final int INDEX_FIRST_TASK = 0;
    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_WORD_SHORT = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_SHORT
            + ": Sorts all tasks by either start date, end date or priority. "
            + "Parameters: SORT_TYPE (sd or ed or p)\n"
            + "Example: " + COMMAND_WORD + " sd\n"
            + "Example: " + COMMAND_WORD_SHORT + " ed";

    public static final String MESSAGE_SUCCESS = "Sorted all tasks.";

    private SortBy sortType;

    public SortCommand(SortBy sortType) {
        this.sortType = sortType;
    }

    @Override
    public CommandResult execute() throws CommandException, IllegalValueException, InvalidDatesException {
        assert model != null;

        try {
            model.sortTasks(sortType);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(INDEX_FIRST_TASK));
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }
    }

}
