package seedu.geekeep.logic.commands;

import seedu.geekeep.logic.commands.exceptions.CommandException;
import seedu.geekeep.model.Model.NothingToRedoException;

/**
 * Lists all persons in the task manager to the user.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Redid previous undid command";
    public static final String MESSAGE_NOTHING_TO_REDO = "Nothing to redo";

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.redo();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (NothingToRedoException e) {
            throw new CommandException(MESSAGE_NOTHING_TO_REDO);
        }
    }
}
