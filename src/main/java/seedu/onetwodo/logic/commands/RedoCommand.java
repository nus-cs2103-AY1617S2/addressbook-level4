package seedu.onetwodo.logic.commands;

import seedu.onetwodo.commons.exceptions.EmptyHistoryException;
import seedu.onetwodo.logic.commands.exceptions.CommandException;

//@@author A0135739W
/**
 * Redo the most recent undo command that modifies the toDoList.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Redo the most recent undo command that modifies any of the 3 lists.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Redo successful.";

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.redo();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyHistoryException ehe) {
            throw new CommandException(ehe.getMessage());
        }
    }

}
