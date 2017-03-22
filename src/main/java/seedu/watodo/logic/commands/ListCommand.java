package seedu.watodo.logic.commands;

import seedu.watodo.commons.exceptions.IllegalValueException;

/**
 * Lists all overdue tasks and upcoming tasks due the next day in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all overdue tasks and tasks due tomorrow";
    
    public static final int DEADLINE = 1;

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
