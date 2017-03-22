package seedu.watodo.logic.commands;

import seedu.watodo.commons.exceptions.IllegalValueException;

/**
 * Lists all tasks scheduled on the current day in the task manager to the user.
 */
public class ListDayCommand extends ListCommand {

    public static final String COMMAND_WORD = "day";

    public static final String MESSAGE_SUCCESS = "Listed today's tasks";
    
    public static final int DEADLINE = 0;

    @Override
    public CommandResult execute() {
        try {
            model.updateFilteredByDatesTaskList(DEADLINE);
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
