package seedu.taskboss.logic.commands;

import java.util.EmptyStackException;

/**
 * Undoes the most recent operation entered by user.
 */

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes the most recent operation entered by user\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "The most recent operation has been undone!";

    public static final String MESSAGE_WITHOUT_PREVIOUS_OPERATION = "Cannot undo.";

    public UndoCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.undoTaskboss();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyStackException ese) {
            return new CommandResult(MESSAGE_WITHOUT_PREVIOUS_OPERATION);
        }
    }
}
