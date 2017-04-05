package seedu.tache.logic.commands;

import seedu.tache.model.TaskManager;

/**
 * Clears the task manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "All tasks has been cleared!";
    //@@author A0150120H
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": deletes all tasks\n"
            + "WARNING: THIS CANNOT BE UNDONE\n"
            + "Example: " + COMMAND_WORD;
    //@@author

    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TaskManager());
        clearUndoHistory();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
