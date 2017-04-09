package seedu.opus.logic.commands;

import seedu.opus.logic.commands.exceptions.CommandException;
import seedu.opus.model.util.InvalidUndoException;

//@@author A0148087W
/**
 * Rollback the most recent changes by Undo Command to TaskManager
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo to previous state!";

    @Override
    public CommandResult execute() throws CommandException {
        try {
            assert model != null;
            model.resetToPrecedingState();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (InvalidUndoException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
