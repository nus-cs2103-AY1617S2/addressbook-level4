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
 * Indicates an existing task is done in the task manager.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Task Done: %1$s";
    public static final String MESSAGE_SUCCESS_STATUS_BAR = "Task done successfully.";

    private final int filteredTaskListIndex;

    /**
     * @param filteredTaskListIndex
     *            the index of the task in the filtered task list to edit
     */
    public DoneCommand(int filteredTaskListIndex) {
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

        ReadOnlyTask taskToDone = lastShownList.get(filteredTaskListIndex);
        try {
            Task doneTask = createDoneTask(taskToDone);
            model.updateTask(filteredTaskListIndex, doneTask);
            model.updateFilteredListToShowAll();
            return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, doneTask), MESSAGE_SUCCESS_STATUS_BAR);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(EditCommand.MESSAGE_DUPLICATE_TASK);
        } catch (IllegalValueException e) {
            // Should not Happen
            throw new CommandException(e.getMessage());
        }

    }

    /**
     * Creates and returns a {@code Task} that is done
     *
     * @throws IllegalValueException
     */
    private static Task createDoneTask(ReadOnlyTask taskToDone) throws IllegalValueException {
        assert taskToDone != null;

        boolean updatedDone = true;
        return Task.createTask(taskToDone.getName(), taskToDone.getTags(), taskToDone.getDeadline(),
                taskToDone.getStartingTime(), updatedDone, taskToDone.isManualToday());
    }
}
