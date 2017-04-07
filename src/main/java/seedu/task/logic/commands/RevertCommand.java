//@@author A0144813J
package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.Model.StateLimitReachedException;

/**
 * Undo all state-changing commands in the session.
 */
public class RevertCommand extends Command {

    public static final String COMMAND_WORD = "revert";

    public static final String MESSAGE_SUCCESS = "Reverted";

    /**
     * Creates a Revert command.
     */
    public RevertCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
      //@@author A0144813J
        assert model != null;
        try {
            model.setTaskManagerStateToZero();
        } catch (StateLimitReachedException e) {
            throw new CommandException(UndoCommand.MESSAGE_NO_BACKWARDS_COMMAND);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
      //@@author
    }

}
