package seedu.tasklist.logic.commands;

import java.util.List;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.logic.commands.exceptions.CommandException;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList;

/**
 * Marks a task as done using its last displayed index from FlexiTask
 * @author A0143355J
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the last task listing as done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Done Task: %1$s";

    public final int targetIndex;

    public DoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDone = lastShownList.get(targetIndex - 1);

        Task doneTask;

        doneTask = createDoneTask(taskToDone);

        model.updateTask(targetIndex, doneTask);

        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToDone));
        return null;
    }

}
