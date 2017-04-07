package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.Model.StateLimitReachedException;

/**
 * Redo a command in Burdens.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Redone";
    public static final String MESSAGE_NO_FORWARDS_COMMAND = "There's no command to redo.";

    /**
     * Creates a Redo Command.
     */
    public RedoCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
      //@@author A0144813J
        assert model != null;
        try {
            model.setTaskManagerStateForwards();
        } catch (StateLimitReachedException e) {
            throw new CommandException(MESSAGE_NO_FORWARDS_COMMAND);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
      //@@author
    }
}
