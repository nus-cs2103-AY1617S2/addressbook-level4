package seedu.watodo.logic.commands;

//@@author A0139845R
/**
 * Redo commands from the undo history stack each time command is executed
 * until no more undo history left.
 */

public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Undo reverted.";
    public static final String MESSAGE_EMPTY = "No command left to redo.";
    public static final String MESSAGE_FAILURE = "Failed to redo";



    @Override
    public CommandResult execute() {
        assert model != null;
        Command undoneCommand = model.getUndoneCommand();
        if (undoneCommand != null) {
            undoneCommand.redo();

            return new CommandResult(MESSAGE_SUCCESS);
        }

        return new CommandResult(MESSAGE_EMPTY);
    }
}
