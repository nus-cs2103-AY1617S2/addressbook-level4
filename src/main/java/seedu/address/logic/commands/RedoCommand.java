package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.exceptions.NoPreviousCommandException;

/*
 * Undo the last command by the user.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_NO_PREV_COMMAND = "No commands left to redo";
    public static final String MESSAGE_SUCCESS_STATUS_BAR = "Redo successfully.";

    @Override
    public CommandResult execute() {
        return new CommandResult("Should not execute", MESSAGE_SUCCESS_STATUS_BAR);
    }

    public String getToRedo() throws CommandException {
        try {
            return model.getRedoCommand();
        } catch (NoPreviousCommandException e) {
            throw new CommandException(MESSAGE_NO_PREV_COMMAND);
        }
    }

}
