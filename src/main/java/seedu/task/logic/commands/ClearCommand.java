package seedu.task.logic.commands;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.Task;

/**
 * Clears all task.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD_1 = "clear";
    public static final String MESSAGE_SUCCESS = "KIT has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Clears all tasks in KIT.\n"
            + "Example: " + COMMAND_WORD_1;

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
