package seedu.today.logic.commands;

import java.util.List;

import seedu.today.commons.core.Messages;
import seedu.today.commons.exceptions.IllegalValueException;
import seedu.today.logic.commands.exceptions.CommandException;
import seedu.today.model.task.ReadOnlyTask;
import seedu.today.model.task.Task;
import seedu.today.model.task.UniqueTaskList;

//@@author A0093999Y
/**
 * Indicates an existing task does not need to be done today in the task
 * manager. In other words, move it out of the Today list but only if it isn't
 * due today.
 */
public class NotTodayCommand extends Command {

    public static final String COMMAND_WORD = "nottoday";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Indicates that the task identified"
            + "by the index number used in the last task listing does not need to be done today.\n"
            + "Parameters: INDEX (must be a positive integer) \n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_NOTTODAY_TASK_SUCCESS = "Task not for Today: %1$s";

    private final int filteredTaskListIndex;

    /**
     * @param filteredTaskListIndex
     *            the index of the task in the filtered task list to edit
     */
    public NotTodayCommand(int filteredTaskListIndex) {
        assert filteredTaskListIndex > 0;
        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToNotToday = lastShownList.get(filteredTaskListIndex);
        try {
            Task todayTask = createNotTodayTask(taskToNotToday);
            model.updateTask(filteredTaskListIndex, todayTask);
            model.updateFilteredListToShowAll();
            return new CommandResult(String.format(MESSAGE_NOTTODAY_TASK_SUCCESS, todayTask),
                    String.format(MESSAGE_NOTTODAY_TASK_SUCCESS, todayTask.getID()));
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(EditCommand.MESSAGE_DUPLICATE_TASK);
        } catch (IllegalValueException e) {
            // Should not Happen
            throw new CommandException(e.getMessage());
        }

    }

    /**
     * Creates and returns a {@code Task} that should be done today
     *
     * @throws IllegalValueException
     */
    private static Task createNotTodayTask(ReadOnlyTask taskToNotToday) throws IllegalValueException {
        assert taskToNotToday != null;

        boolean updatedToday = false;
        return Task.createTask(taskToNotToday.getName(), taskToNotToday.getTags(), taskToNotToday.getDeadline(),
                taskToNotToday.getStartingTime(), taskToNotToday.isDone(), updatedToday);
    }
}
