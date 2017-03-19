package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.StateManager;

/**
 * Undo most recent command
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undid most recent command";
    private final StateManager stateManager = StateManager.getInstance();

    @Override
    public CommandResult execute() throws CommandException {
        stateManager.undo();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
