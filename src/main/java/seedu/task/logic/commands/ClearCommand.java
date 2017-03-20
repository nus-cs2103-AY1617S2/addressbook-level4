package seedu.task.logic.commands;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.Task;

/**
 * Clears all task.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_WORD_HOTKEY = "cl";
    public static final String MESSAGE_SUCCESS = "KIT has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.resetData(new TaskManager());
        } catch (IllegalValueException e) {
            return new CommandResult(Task.MESSAGE_TASK_CONSTRAINTS);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
