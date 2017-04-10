//@@author A0147622H
package seedu.geekeep.logic.commands;

import seedu.geekeep.logic.commands.exceptions.CommandException;
import seedu.geekeep.model.Model.NothingToUndoException;

/**
 * Lists all tasks in GeeKeep to the user.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undid command: %1$s";
    public static final String MESSAGE_NOTHING_TO_UNDO = "Nothing to undo";

    @Override
    public CommandResult execute() throws CommandException {
        try {
            String commandTextUndid = model.undo();
            return new CommandResult(String.format(MESSAGE_SUCCESS, commandTextUndid));
        } catch (NothingToUndoException e) {
            throw new CommandException(MESSAGE_NOTHING_TO_UNDO);
        }
    }
}
