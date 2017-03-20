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
    public static final String MESSAGE_DONE_ERROR = "Task is already marked as done!";

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

        //Index adjusted to 0 based
        int adjustedIndex = targetIndex - 1;

        ReadOnlyTask taskToDone = lastShownList.get(adjustedIndex);

        Task doneTask;

        try {
            doneTask = createDoneTask(taskToDone);
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }

        try {
            model.updateTask(adjustedIndex, doneTask);
        } catch (DuplicateTaskException e) {
            System.out.println("here");
            assert false;
        }

        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToDone));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToDone}
     * edited with status as COMPLETED.
     * @throws IllegalValueException
     */
    private Task createDoneTask(ReadOnlyTask taskToDone) throws IllegalValueException {
        assert taskToDone != null;

        Status status = taskToDone.getStatus();
        if (status.status == Status.COMPLETED) {
            throw new IllegalValueException(MESSAGE_DONE_ERROR);
        }

        String type = taskToDone.getType();
        switch (type) {
        case FloatingTask.TYPE:
            FloatingTask doneFloatingTask = new FloatingTask((ReadOnlyFloatingTask) taskToDone);
            doneFloatingTask.getStatus().status = Status.COMPLETED;
            return doneFloatingTask;

        case DeadlineTask.TYPE:
            DeadlineTask doneDeadlineTask = new DeadlineTask((ReadOnlyDeadlineTask) taskToDone);
            doneDeadlineTask.getStatus().status = Status.COMPLETED;
            return doneDeadlineTask;

        case EventTask.TYPE:
            EventTask doneEventTask = new EventTask((ReadOnlyEventTask) taskToDone);
            doneEventTask.getStatus().status = Status.COMPLETED;
            return doneEventTask;

        default:
            return null;
        }
    }

}
