package savvytodo.logic.commands;

import savvytodo.logic.commands.exceptions.CommandException;
import savvytodo.model.undoredo.exceptions.UndoFailureException;

//@@A0124863A
/**
 * @author A0124863A
 * Undo an operation to the task manager
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo an operation to the task manager";
    public static final String MESSAGE_SUCCESS = "undo successfully";
    public static final String MESSAGE_FAILURE = "cannot undo";

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.undo();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (UndoFailureException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

}
