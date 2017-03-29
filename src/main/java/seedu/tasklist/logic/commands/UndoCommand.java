package seedu.tasklist.logic.commands;

import java.util.EmptyStackException;

//@@author A0139747N
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_SUCCESS = "Reverted the most recent action: ";
    public static final String MESSAGE_UNDO_FAILED = "No command to undo.";

    @Override
    public CommandResult execute() {
        try {
            String userInput = model.setPreviousState();
            return new CommandResult(MESSAGE_UNDO_SUCCESS + userInput);
        } catch (EmptyStackException e) {
            return new CommandResult(MESSAGE_UNDO_FAILED);
        }
    }
}
