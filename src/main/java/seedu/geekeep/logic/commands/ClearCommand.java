package seedu.geekeep.logic.commands;

import seedu.geekeep.model.GeeKeep;

/**
 * Clears the Task Manager.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task Manager has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new GeeKeep());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
