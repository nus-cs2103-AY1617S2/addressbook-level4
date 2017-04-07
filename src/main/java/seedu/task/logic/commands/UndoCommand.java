package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.Model.StateLimitReachedException;

/**
 * Undo a command in Burdens
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undone";
    public static final String MESSAGE_NO_BACKWARDS_COMMAND = "There's no previous command to undo.";

    /**
     * Creates an Undo command.
     */
    public UndoCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
      //@@author A0144813J
        assert model != null;
        try {
            model.setTaskManagerStateBackwards();
        } catch (StateLimitReachedException e) {
            throw new CommandException(MESSAGE_NO_BACKWARDS_COMMAND);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
      //@@author
    }

}
