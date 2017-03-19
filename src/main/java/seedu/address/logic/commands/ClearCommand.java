package seedu.address.logic.commands;

import seedu.address.logic.GlobalStack;
import seedu.address.model.TaskManager;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "doTASK has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        GlobalStack gStack = GlobalStack.getInstance();
        gStack.getUndoStack().push(model.getTaskManager().getTaskList());
        model.resetData(new TaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
