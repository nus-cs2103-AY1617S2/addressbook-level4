package seedu.address.logic.commands;

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
        model.resetData(new WhatsLeft());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
