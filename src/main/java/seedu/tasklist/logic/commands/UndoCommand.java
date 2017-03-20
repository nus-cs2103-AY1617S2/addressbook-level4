package seedu.tasklist.logic.commands;

import java.util.EmptyStackException;

public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_SUCCESS = "Reverted to the most recent action.";
    public static final String MESSAGE_UNDO_FAILED = "No command to undo.";

    @Override
    public CommandResult execute() {
        try {
            model.setPreviousState();
            return new CommandResult(MESSAGE_UNDO_SUCCESS);
        } catch (EmptyStackException e) {
            return new CommandResult(MESSAGE_UNDO_FAILED);
        }
    }
}
