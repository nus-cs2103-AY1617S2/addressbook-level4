//@@author A0147622H
package seedu.geekeep.logic.commands;

import seedu.geekeep.logic.commands.exceptions.CommandException;
import seedu.geekeep.model.Model.NothingToRedoException;

/**
 * Lists all tasks in GeeKeep to the user.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Redid previous undid command: %1$s";
    public static final String MESSAGE_NOTHING_TO_REDO = "Nothing to redo";

    @Override
    public CommandResult execute() throws CommandException {
        try {
            String commandTextredid = model.redo();
            return new CommandResult(String.format(MESSAGE_SUCCESS, commandTextredid));
        } catch (NothingToRedoException e) {
            throw new CommandException(MESSAGE_NOTHING_TO_REDO);
        }
    }
}
