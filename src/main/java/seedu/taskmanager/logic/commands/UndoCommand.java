package seedu.taskmanager.logic.commands;

import java.util.EmptyStackException;

import seedu.taskmanager.logic.commands.exceptions.CommandException;
import seedu.taskmanager.model.TaskManager;

/**
 * Undo the previous action in task manager.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "UNDO";
    public static final String MESSAGE_SUCCESS = "Previous action has been undone.";
    public static final String MESSAGE_FAILURE = "Nothing to undo.";

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.undoTaskManager();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyStackException ese) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
