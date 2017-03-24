package seedu.address.logic.commands;

import seedu.address.model.AddressBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "reset";
    public static final String MESSAGE_SUCCESS = "Task manager has been reset!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
