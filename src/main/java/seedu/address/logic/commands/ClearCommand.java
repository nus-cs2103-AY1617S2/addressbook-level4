package seedu.address.logic.commands;

import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyWhatsLeft;
import seedu.address.model.WhatsLeft;

/**
 * Clears WhatsLeft.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "WhatsLeft has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        ReadOnlyWhatsLeft currState = model.getWhatsLeft();
        ModelManager.setPreviousState(currState);
        model.resetData(new WhatsLeft());
        //store for undo operation
        model.storePreviousCommand("clear");
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
