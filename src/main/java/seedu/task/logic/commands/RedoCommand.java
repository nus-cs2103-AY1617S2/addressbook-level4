//@@author A0113795Y
package seedu.task.logic.commands;

import java.util.EmptyStackException;

import seedu.task.logic.commands.exceptions.CommandException;

/**
 * Redo the previous change undone to the Task Manager
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Preivous undo restored.";
    public static final String MESSAGE_INVALID_REDO_COMMAND = "Nothing to redo!";

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.redo();
        } catch (EmptyStackException ese) {
            throw new CommandException(MESSAGE_INVALID_REDO_COMMAND);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
