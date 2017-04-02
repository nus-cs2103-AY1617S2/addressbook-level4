//@@author A0139539R
package seedu.task.logic.commands;

import seedu.task.model.TaskManager;

/**
 * Resets the task manager.
 */
public class ResetCommand extends Command {

    public static final String COMMAND_WORD = "reset";
    public static final String MESSAGE_SUCCESS = "Task manager has been reset!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
