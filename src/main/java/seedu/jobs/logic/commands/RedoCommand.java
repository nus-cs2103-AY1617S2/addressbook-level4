package seedu.jobs.logic.commands;

import seedu.jobs.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Redo previoud undoed commands.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Redo previous command";


    @Override
    public CommandResult execute() {
        try {
            model.redoCommand();
        } catch (TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
