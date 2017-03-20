package seedu.tasklist.logic.commands;

import java.util.EmptyStackException;

public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_REDO_SUCCESS = "Redid the most recent action that is undone.";
    public static final String MESSAGE_REDO_FAILED = "No command to redo.";

    @Override
    public CommandResult execute() {
        try {
            model.setNextState();
            return new CommandResult(MESSAGE_REDO_SUCCESS);
        } catch (EmptyStackException e) {
            return new CommandResult(MESSAGE_REDO_FAILED);
        }
    }

}
