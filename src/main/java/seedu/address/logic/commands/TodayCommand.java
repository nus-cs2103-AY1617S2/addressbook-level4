package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

//@@author A0093999Y
/**
 * Indicates an existing task should be done today in the task manager. In other
 * words, place it in the Today list.
 */
public class TodayCommand extends Command {

    public static final String COMMAND_WORD = "today";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Indicates that the task identified"
            + "by the index number used in the last task listing should be done today.\n"
            + "Parameters: INDEX (must be a positive integer) \n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_TODAY_TASK_SUCCESS = "Task for Today: %1$s";
    public static final String MESSAGE_SUCCESS_STATUS_BAR = "Task for Today: %1$s";

    private final int filteredTaskListIndex;

    /**
     * @param filteredTaskListIndex
     *            the index of the task in the filtered task list to edit
     */
    public TodayCommand(int filteredTaskListIndex) {
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

        ReadOnlyTask taskToToday = lastShownList.get(filteredTaskListIndex);
        try {
            Task todayTask = createTodayTask(taskToToday);
            todayTask.setAnimation(2);
            model.updateTask(filteredTaskListIndex, todayTask);
            model.updateFilteredListToShowAll();
            return new CommandResult(String.format(MESSAGE_TODAY_TASK_SUCCESS, todayTask),
                    String.format(MESSAGE_TODAY_TASK_SUCCESS, todayTask.getID()));
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
    private static Task createTodayTask(ReadOnlyTask taskToToday) throws IllegalValueException {
        assert taskToToday != null;

        boolean updatedToday = true;
        return Task.createTask(taskToToday.getName(), taskToToday.getTags(), taskToToday.getDeadline(),
                taskToToday.getStartingTime(), taskToToday.isDone(), updatedToday);
    }
}
