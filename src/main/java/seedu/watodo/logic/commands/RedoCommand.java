package seedu.watodo.logic.commands;

//@@author A0139845R
/**
 * Redo the last undo saved in undo history stack in model
 */

public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "redo success.";
    public static final String MESSAGE_EMPTY = "No command left to redo.";
    public static final String MESSAGE_FAILURE = "Failed to redo";



    @Override
    public CommandResult execute() {
        assert model != null;
        Command undoneCommand = model.getUndoneCommand();
        if (undoneCommand != null) {
            undoneCommand.redo();

            return new CommandResult(undoneCommand + " " + MESSAGE_SUCCESS);
        }

        return new CommandResult(MESSAGE_EMPTY);
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
