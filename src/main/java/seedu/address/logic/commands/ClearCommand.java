package seedu.address.logic.commands;

import seedu.address.model.TaskList;

/**
 * Clears the task manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task Manager has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TaskList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
