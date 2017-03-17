package seedu.task.logic.commands;

import seedu.task.model.TaskManager;

/**
 * Clears all task.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_WORD_HOTKEY = "cl";
    public static final String MESSAGE_SUCCESS = "KIT has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
