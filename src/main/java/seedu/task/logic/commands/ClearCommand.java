package seedu.task.logic.commands;

import seedu.task.logic.GlobalStack;
import seedu.task.model.TaskManager;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "doTASK has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        //@@author A0139161J
        GlobalStack gStack = GlobalStack.getInstance();
        // Pushes the most updated list of the task manager into undo stack before wiping data
        TaskManager tm = new TaskManager(model.getTaskManager());
        gStack.getUndoStack().push(tm);
        //@@author
        model.resetData(new TaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
