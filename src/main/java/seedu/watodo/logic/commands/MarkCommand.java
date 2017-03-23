package seedu.watodo.logic.commands;

import seedu.watodo.commons.core.Messages;
import seedu.watodo.commons.core.UnmodifiableObservableList;
import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.exceptions.CommandException;
import seedu.watodo.model.task.ReadOnlyTask;
import seedu.watodo.model.task.Task;
import seedu.watodo.model.task.TaskStatus;
import seedu.watodo.model.task.UniqueTaskList;

/**
 * Marks a task identified using it's last displayed index from the task manager as completed.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the status of the task identified to done "
            + "by the index number used in the last task listing as completed.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Task completed: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String MESSAGE_STATUS_DONE = "The task status is already set to Done.";

    private final int filteredTaskListIndex;

    public MarkCommand(int filteredTaskListIndex) {
        assert filteredTaskListIndex > 0;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;
    }

    @Override
    public CommandResult execute() throws CommandException {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMark = lastShownList.get(filteredTaskListIndex);

        try {
            Task markedTask = createMarkedTask(taskToMark);
            model.updateTask(filteredTaskListIndex, markedTask);

        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);

        } catch (IllegalValueException ive) {
            throw new CommandException (MESSAGE_STATUS_DONE);
        }

        // TODO: TaskNotFoundException?
        // TODO: Remove from list and update list
        model.updateFilteredListToShowAll();

        return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToMark}
     */
    private static Task createMarkedTask(ReadOnlyTask taskToMark) throws IllegalValueException {
        assert taskToMark != null;

        if (taskToMark.getStatus() == TaskStatus.DONE) {
            throw new IllegalValueException(MESSAGE_STATUS_DONE);
        }

        Task markedTask = new Task(taskToMark.getDescription(), taskToMark.getStartDate(),
                                   taskToMark.getEndDate(), taskToMark.getTags());
        markedTask.setStatus(TaskStatus.DONE);

        return markedTask;
    }

}
