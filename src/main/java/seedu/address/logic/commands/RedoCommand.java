package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.StateManager;

/**
 * Redo most recent command
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redid most recent command";
    public static final String MESSAGE_FAIL = "No command to redo";

    private final StateManager stateManager = StateManager.getInstance();

    @Override
    public CommandResult execute() throws CommandException, IllegalValueException {
        if (stateManager.redoStackHasCommands()) {
            stateManager.redo();
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_FAIL);
        }
    }
}
