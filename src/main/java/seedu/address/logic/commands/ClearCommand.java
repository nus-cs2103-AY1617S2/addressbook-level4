package seedu.address.logic.commands;

import seedu.address.model.ToDoApp;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "ToDoApp has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new ToDoApp());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
