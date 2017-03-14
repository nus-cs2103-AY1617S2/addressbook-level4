package seedu.doit.logic.commands;

import seedu.doit.model.TaskManager;

/**
 * Clears all the tasks.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "All tasks has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
