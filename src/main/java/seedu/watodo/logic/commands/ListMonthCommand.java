package seedu.watodo.logic.commands;

import seedu.watodo.commons.exceptions.IllegalValueException;

/**
 * Lists all tasks scheduled on the current month in the task manager to the user.
 */
public class ListMonthCommand extends ListCommand {

    public static final String COMMAND_WORD = "month";

    public static final String MESSAGE_SUCCESS = "Listed this month's tasks";
    
    public static final int DEADLINE = 1;

    @Override
    public CommandResult execute() {
        try {
            model.updateFilteredByMonthsTaskList(DEADLINE);
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
