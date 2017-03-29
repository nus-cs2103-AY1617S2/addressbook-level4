package seedu.watodo.logic.commands;

import seedu.watodo.commons.core.Messages;
import seedu.watodo.commons.core.UnmodifiableObservableList;
import seedu.watodo.logic.commands.exceptions.CommandException;
import seedu.watodo.model.task.ReadOnlyTask;
import seedu.watodo.model.task.Task;
import seedu.watodo.model.task.TaskStatus;
import seedu.watodo.model.task.UniqueTaskList;

/**
 * Marks a task identified using it's last displayed index from the task manager
 * as completed.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the status of the task identified to done "
            + "by the index number used in the last task listing as completed.\n"
            + "Parameters: INDEX (must be a positive integer) [MORE_INDICES]\n" + "Example: " + COMMAND_WORD
            + " 1 2";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Task completed: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String MESSAGE_STATUS_DONE = "The task status is already set to Done.";

    private int[] filteredTaskListIndices;

    public MarkCommand(int[] args) {
        this.filteredTaskListIndices = args;

        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            assert filteredTaskListIndices != null;
            assert filteredTaskListIndices.length > 0;
            assert filteredTaskListIndices[i] > 0;

            // converts filteredTaskListIndex to from one-based to zero-based.
            filteredTaskListIndices[i] = filteredTaskListIndices[i] - 1;
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        final StringBuilder tasksMarkedMessage = new StringBuilder();

        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

            if (filteredTaskListIndices[i] >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyTask taskToMark = lastShownList.get(filteredTaskListIndices[i]);

            try {
                Task markedTask = createMarkedTask(taskToMark);
                model.updateTask(filteredTaskListIndices[i], markedTask);

            } catch (UniqueTaskList.DuplicateTaskException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            }

            tasksMarkedMessage.append(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark) + "\n");
        }

        return new CommandResult(tasksMarkedMessage.toString());
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToMark}
     */
    private static Task createMarkedTask(ReadOnlyTask taskToMark) throws CommandException {
        assert taskToMark != null;

        if (taskToMark.getStatus() == TaskStatus.DONE) {
            throw new CommandException(MESSAGE_STATUS_DONE);
        }

        Task markedTask = new Task(taskToMark.getDescription(), taskToMark.getStartDate(), taskToMark.getEndDate(),
                taskToMark.getTags());
        markedTask.setStatus(TaskStatus.DONE);

        return markedTask;
    }

}
