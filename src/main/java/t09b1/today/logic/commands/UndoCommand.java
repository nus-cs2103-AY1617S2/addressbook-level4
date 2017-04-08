package t09b1.today.logic.commands;

import t09b1.today.logic.commands.exceptions.CommandException;
import t09b1.today.model.exceptions.NoPreviousCommandException;

// @@author A0139388M
/*
 * Undo the last command by the user.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo your last command.\n";
    public static final String MESSAGE_SUCCESS = "We have reversed your last command: %1$s";
    public static final String MESSAGE_NO_PREV_COMMAND = "No commands left to undo";
    public static final String MESSAGE_SUCCESS_STATUS_BAR = "We have reversed your last command: %1$s";

    @Override
    public CommandResult execute() throws CommandException {
        try {
            String prevCommand = model.undoLastCommand();
            return new CommandResult(String.format(MESSAGE_SUCCESS, prevCommand),
                    String.format(MESSAGE_SUCCESS_STATUS_BAR, prevCommand));
        } catch (NoPreviousCommandException e) {
            throw new CommandException(MESSAGE_NO_PREV_COMMAND);
        }
    }

}
