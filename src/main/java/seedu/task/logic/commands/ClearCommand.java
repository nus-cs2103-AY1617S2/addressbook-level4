package seedu.task.logic.commands;

import seedu.task.model.TaskManager;

/**
 * Clears all task.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD_1 = "clear";
    public static final String MESSAGE_SUCCESS = "KIT has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
