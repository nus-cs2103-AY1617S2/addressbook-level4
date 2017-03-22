package seedu.task.logic.commands;

import java.util.List;

import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.NoChangeException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Complete;
import seedu.task.model.task.Description;
import seedu.task.model.task.DueDate;
import seedu.task.model.task.Duration;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskId;
import seedu.task.model.task.UniqueTaskList;

/**
 * Completes a task identified using it's last displayed index from the task list.
 */
public class UncompleteCommand extends Command {

    public static final String COMMAND_WORD = "uncomplete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the task identified "
            + "by the index number used in the last task listing as uncomplete. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNCOMPLETE_TASK_SUCCESS = "Task: %1$s marked uncomplete";

    public static final String MESSAGE_TASK_ALREADY_UNCOMPLETE = "Task already marked uncomplete.";

    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list.";

    public final int targetIndex;

    public UncompleteCommand(int targetIndex) {
        this.targetIndex = targetIndex - 1;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (targetIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUncomplete = lastShownList.get(targetIndex);

        try {
            Task uncompletedTask = createUncompletedTask(taskToUncomplete);
            model.updateTask(targetIndex, uncompletedTask);
        } catch (NoChangeException nce) {
            throw new CommandException(MESSAGE_TASK_ALREADY_UNCOMPLETE);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_UNCOMPLETE_TASK_SUCCESS, taskToUncomplete));
    }

    private static Task createUncompletedTask(ReadOnlyTask taskToComplete) throws NoChangeException {
        if (!taskToComplete.getComplete().getCompletion()) {
            throw new NoChangeException(MESSAGE_TASK_ALREADY_UNCOMPLETE);
        }
        Description description = taskToComplete.getDescription();
        UniqueTagList tags = taskToComplete.getTags();
        Duration duration = taskToComplete.getDuration();
        DueDate dueDate = taskToComplete.getDueDate();
        Complete complete = new Complete(false);
        TaskId id = taskToComplete.getTaskId();

        Task uncompletedTask = new Task(description, dueDate, duration, tags, complete, id);

        return uncompletedTask;
    }

}
