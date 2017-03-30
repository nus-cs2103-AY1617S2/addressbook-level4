package seedu.watodo.logic.commands;

import seedu.watodo.commons.core.Messages;
import seedu.watodo.commons.core.UnmodifiableObservableList;
import seedu.watodo.logic.commands.exceptions.CommandException;
import seedu.watodo.model.task.ReadOnlyTask;
import seedu.watodo.model.task.Task;
import seedu.watodo.model.task.TaskStatus;
import seedu.watodo.model.task.UniqueTaskList;
import seedu.watodo.model.task.UniqueTaskList.DuplicateTaskException;

//@@author A0141077L-reused
/**
 * Marks a task identified using it's last displayed index from the task manager
 * as undone.
 */
public class UnmarkCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the status of the task identified to undone "
            + "by the index number used in the last task listing as completed.\n"
            + "Parameters: INDEX (must be a positive integer) [MORE_INDICES]\n" + "Example: " + COMMAND_WORD
            + " 1 2";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Task undone: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String MESSAGE_STATUS_UNDONE = "The task status is already set to Undone.";

    private int[] filteredTaskListIndices;

    private Task undoUnmark;
    private int undoUnmarkInt;

    public UnmarkCommand(int[] args) {
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
        final StringBuilder tasksUnmarkedMessage = new StringBuilder();

        for (int i = 0; i < filteredTaskListIndices.length; i++) {
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

            if (filteredTaskListIndices[i] >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyTask taskToUnmark = lastShownList.get(filteredTaskListIndices[i]);
            this.undoUnmark = new Task(taskToUnmark);

            try {
                Task unmarkedTask = createUnmarkedTask(taskToUnmark);
                model.updateTask(filteredTaskListIndices[i], unmarkedTask);
                this.undoUnmarkInt = filteredTaskListIndices[i];

            } catch (UniqueTaskList.DuplicateTaskException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            }

            tasksUnmarkedMessage.append(String.format(MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark) + "\n");
        }

        return new CommandResult(tasksUnmarkedMessage.toString());
    }

    //@@author A0139845R

    @Override
    public void unexecute() {
        try {
            model.updateTask(undoUnmarkInt, undoUnmark);
        } catch (DuplicateTaskException e) {

        }
    }

    @Override
    public void redo() {
        try {
            model.updateFilteredListToShowAll();
            this.execute();
        } catch (CommandException e) {
        }
    }
    //@@author

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToUnmark}
     */
    private static Task createUnmarkedTask(ReadOnlyTask taskToUnmark) throws CommandException {
        assert taskToUnmark != null;

        if (taskToUnmark.getStatus() == TaskStatus.UNDONE) {
            throw new CommandException(MESSAGE_STATUS_UNDONE);
        }

        Task unmarkedTask = new Task(taskToUnmark.getDescription(), taskToUnmark.getStartDate(),
                taskToUnmark.getEndDate(), taskToUnmark.getTags());
        unmarkedTask.setStatus(TaskStatus.UNDONE);

        return unmarkedTask;
    }

}
