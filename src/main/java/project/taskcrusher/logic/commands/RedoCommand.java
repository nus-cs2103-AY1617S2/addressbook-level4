package project.taskcrusher.logic.commands;

import project.taskcrusher.logic.commands.exceptions.CommandException;

/**
 * re-does the previously performed undo by resetting the state of the userInbox.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo Performed";
    public static final String MESSAGE_FAILURE = "No Redo to perform";

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        boolean isRedoSuccessful = model.redo();
        if (isRedoSuccessful) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
