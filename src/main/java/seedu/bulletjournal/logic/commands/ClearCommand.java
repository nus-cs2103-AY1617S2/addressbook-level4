package seedu.bulletjournal.logic.commands;

import seedu.bulletjournal.model.TodoList;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Todo list has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TodoList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
