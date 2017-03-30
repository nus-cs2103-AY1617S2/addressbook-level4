package seedu.jobs.logic.commands;

import seedu.jobs.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Undo last commands which has modified the data.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undo last command";


    @Override
    public CommandResult execute() {
        try {
            model.undoCommand();
        } catch (TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
