package savvytodo.logic.commands;

import savvytodo.logic.commands.exceptions.CommandException;
import savvytodo.model.undoredo.exceptions.RedoFailureException;

//@@A0124863A
/**
 * @author A0124863A
 * Redo an operation to the task manager
 *
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redo an operation to the task manager";
    public static final String MESSAGE_SUCCESS = "redo successfully";
    public static final String MESSAGE_FAILURE = "cannot redo";

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.redo();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (RedoFailureException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
