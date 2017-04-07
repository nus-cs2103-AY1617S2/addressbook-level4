package project.taskcrusher.logic.commands;

import project.taskcrusher.logic.commands.exceptions.CommandException;

//@@author A0163639W
/**
 * Undoes the previously performed action by resetting the state of the userInbox.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo Performed";
    public static final String MESSAGE_FAILURE = "No undo to perform";

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        boolean isUndoSuccessful = model.undo();
        if (isUndoSuccessful) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
