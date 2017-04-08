package seedu.today.logic.commands;

import seedu.today.model.TaskManager;

/**
 * Clears the task manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "All tasks have been cleared.";
    public static final String MESSAGE_SUCCESS_STATUS_BAR = "All tasks have been cleared.";

    @Override
    public CommandResult execute() {
        assert model != null;
        model.setData(new TaskManager(), true);
        return new CommandResult(MESSAGE_SUCCESS, MESSAGE_SUCCESS_STATUS_BAR);
    }
}
