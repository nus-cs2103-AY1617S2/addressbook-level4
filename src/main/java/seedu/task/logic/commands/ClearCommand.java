package seedu.task.logic.commands;

import seedu.task.model.TaskList;

/**
 * Clears the task manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task Manager has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        TaskList old = new TaskList(model.getTaskList());
        model.getUndoStack().push(old);
        model.resetData(new TaskList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
