//@@author A0113795Y
package seedu.task.logic.commands;

import java.util.EmptyStackException;
import java.util.logging.Logger;

import seedu.task.commons.core.LogsCenter;
import seedu.task.logic.commands.exceptions.CommandException;

/**
 * Undo the previous change to the Task Manager
 */
public class UndoCommand extends Command {
    private static final Logger logger = LogsCenter.getLogger(UndoCommand.class);
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Previous change is undone";
    public static final String MESSAGE_INVALID_UNDO_COMMAND = "Nothing to undo!";

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.undo();
        } catch (EmptyStackException ese) {
            logger.info(MESSAGE_INVALID_UNDO_COMMAND);
            throw new CommandException(MESSAGE_INVALID_UNDO_COMMAND);
        }
        logger.info(MESSAGE_SUCCESS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
