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
import seedu.task.model.task.UniqueTaskList;

/**
 * Completes a task identified using it's last displayed index from the task list.
 */
public class CompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Set completion of task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Completed Task: %1$s, congratulations";

    public static final String TASK_ALREADY_COMPLETED = "You have already completed this task.";

    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list.";

    public final int targetIndex;

    public CompleteCommand(int targetIndex) {
        this.targetIndex = targetIndex - 1;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (targetIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToComplete = lastShownList.get(targetIndex);

        Task completedTask = new Task(taskToComplete);
        try {
            completedTask = createCompletedTask(taskToComplete);
        } catch (NoChangeException nce) {
            throw new CommandException(TASK_ALREADY_COMPLETED);
        }

        try {
            model.updateTask(targetIndex, completedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, taskToComplete));
    }

    private static Task createCompletedTask(ReadOnlyTask taskToComplete) throws NoChangeException {
        Description description = taskToComplete.getDescription();
        UniqueTagList tags = taskToComplete.getTags();
        Duration duration = taskToComplete.getDuration();
        DueDate dueDate = taskToComplete.getDueDate();
        Complete complete = new Complete(true);

        Task completedTask = new Task(description, dueDate, duration, tags, complete);
        if (taskToComplete.getComplete().getCompletion() == true) {
            throw new NoChangeException(TASK_ALREADY_COMPLETED);
        }

        completedTask.setComplete();
        return completedTask;
    }

}

