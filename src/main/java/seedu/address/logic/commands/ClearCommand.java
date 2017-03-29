package seedu.address.logic.commands;

import seedu.address.model.YTomorrow;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "All the tasks have been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new YTomorrow());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
