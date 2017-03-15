package werkbook.task.logic.commands;

import werkbook.task.model.TaskList;

/**
 * Clears the task list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task list has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TaskList());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean isMutable() {
        return true;
    }
}
