package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.ReadOnlyTask.TaskType;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskWithDeadline;
import seedu.address.model.task.TaskWithoutDeadline;
import seedu.address.model.task.UniqueTaskList;

/**
 * Indicates an existing task is not done in the task manager.
 */
public class NotDoneCommand extends Command {

    public static final String COMMAND_WORD = "notdone";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Indicates that the task identified is not done"
            + "by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) \n" + "Example: "
            + COMMAND_WORD + " 1";

    public static final String MESSAGE_NOTDONE_TASK_SUCCESS = "Task Not Done: %1$s";

    private final int filteredTaskListIndex;

    /**
     * @param filteredTaskListIndex
     *            the index of the task in the filtered task list to edit
     */
    public NotDoneCommand(int filteredTaskListIndex) {
        assert filteredTaskListIndex > 0;
        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(
                    Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToNotDone = lastShownList.get(filteredTaskListIndex);
        Task notDoneTask = createDoneTask(taskToNotDone);

        try {
            model.updateTask(filteredTaskListIndex, notDoneTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(EditCommand.MESSAGE_DUPLICATE_PERSON);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(
                String.format(MESSAGE_NOTDONE_TASK_SUCCESS, notDoneTask));
    }

    /**
     * Creates and returns a {@code Task} that is not done
     */
    private static Task createDoneTask(ReadOnlyTask taskToNotDone) {
        assert taskToNotDone != null;

        Name updatedName = taskToNotDone.getName();
        UniqueTagList updatedTags = taskToNotDone.getTags();
        boolean updatedDone = false;
        Task newTask = null;
        if (taskToNotDone.getTaskType() == TaskType.TaskWithNoDeadline) {
            newTask = new TaskWithoutDeadline(taskToNotDone.getName(),
                    updatedTags, updatedDone);
        } else {
            try {
                newTask = new TaskWithDeadline(taskToNotDone);
                newTask.setDone(updatedDone);
            } catch (IllegalValueException e) {
                System.exit(1);
            }
        }
        return newTask;
    }
}
