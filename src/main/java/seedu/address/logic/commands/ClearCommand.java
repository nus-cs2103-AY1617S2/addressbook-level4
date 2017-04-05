package seedu.address.logic.commands;

import seedu.address.model.TaskList;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TaskList());
        //@@author A0148052L-reused
        model.pushCommand(COMMAND_WORD);
        model.pushStatus(model.getTaskList());
        //@@author
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
