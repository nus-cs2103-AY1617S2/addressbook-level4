package seedu.tasklist.logic.commands;

import java.util.List;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.logic.commands.exceptions.CommandException;
import seedu.tasklist.model.task.DeadlineTask;
import seedu.tasklist.model.task.EventTask;
import seedu.tasklist.model.task.FloatingTask;
import seedu.tasklist.model.task.ReadOnlyDeadlineTask;
import seedu.tasklist.model.task.ReadOnlyEventTask;
import seedu.tasklist.model.task.ReadOnlyFloatingTask;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.Status;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Marks a completed task as undone using its last displayed index from FlexiTask
 */
//@@Author A0143355J
public class UndoneCommand extends Command {

    public static final String COMMAND_WORD = "undone";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the last task listing as undone.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNDONE_TASK_SUCCESS = "Undone Task: %1$s";
    public static final String MESSAGE_UNDONE_ERROR = "Task has not been completed!";

    public final int targetIndex;

    public UndoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        //Index adjusted to 0 based
        int adjustedIndex = targetIndex - 1;

        ReadOnlyTask taskToUndone = lastShownList.get(adjustedIndex);

        Task undoneTask;

        try {
            undoneTask = createUndoneTask(taskToUndone);
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }

        try {
            model.updateTask(adjustedIndex, undoneTask);
        } catch (DuplicateTaskException e) {
            assert false;
        }

        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_UNDONE_TASK_SUCCESS, taskToUndone));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToDone}
     * edited with status as COMPLETED.
     * @throws IllegalValueException
     */
    private Task createUndoneTask(ReadOnlyTask taskToUndone) throws IllegalValueException {
        assert taskToUndone != null;

        Status status = taskToUndone.getStatus();
        if (status.value == Status.UNCOMPLETED) {
            throw new IllegalValueException(MESSAGE_UNDONE_ERROR);
        }

        String type = taskToUndone.getType();
        switch (type) {
        case FloatingTask.TYPE:
            FloatingTask undoneFloatingTask = new FloatingTask((ReadOnlyFloatingTask) taskToUndone);
            undoneFloatingTask.setStatus(new Status(Status.UNCOMPLETED));
            return undoneFloatingTask;
        case DeadlineTask.TYPE:
            DeadlineTask undoneDeadlineTask = new DeadlineTask((ReadOnlyDeadlineTask) taskToUndone);
            undoneDeadlineTask.setStatus(new Status(Status.UNCOMPLETED));
            return undoneDeadlineTask;

        case EventTask.TYPE:
            EventTask undoneEventTask = new EventTask((ReadOnlyEventTask) taskToUndone);
            undoneEventTask.setStatus(new Status(Status.UNCOMPLETED));
            return undoneEventTask;

        default:
            return null;
        }
    }

}
