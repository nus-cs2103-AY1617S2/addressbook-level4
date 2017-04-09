package seedu.taskit.logic.commands;

import seedu.taskit.model.TaskManager;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Tasks have been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    //@@author A0141011J
    @Override
    public boolean isUndoable() {
        return true;
    }
}
