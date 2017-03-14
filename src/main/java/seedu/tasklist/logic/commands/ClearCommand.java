package seedu.tasklist.logic.commands;

import seedu.tasklist.model.TaskList;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.enableUndoForClear();
        model.resetData(new TaskList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
