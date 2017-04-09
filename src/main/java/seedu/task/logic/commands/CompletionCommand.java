//@@author A0163744B
package seedu.task.logic.commands;

import java.util.List;

import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.NoChangeException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.logic.history.TaskMemento;
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
 * A class for implementing the complete/uncomplete commands. This class contains all of the logic for
 * changing a task's completion while the complete command and uncomplete command just hold the values
 * specific to the command.
 */
public abstract class CompletionCommand extends Command {
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list.";

    public final int targetIndex;

    public CompletionCommand(int targetIndex) {
        this.targetIndex = targetIndex - 1;
    }

    /**
     * The {@code execute} function of the command. The values passed in tailor the execution for the
     * specific command extending this class.
     *
     * @param shouldMarkComplete whether to mark the task complete or uncomplete
     * @param messageSameState the error message if the task is already completed/uncompleted
     * @param messageCompleteTaskSuccess message if the task is successfully marked complete/uncomplete
     * @return the Command result
     * @throws CommandException contains the error message if something goes wrong with the command.
     */
    protected CommandResult executeCompletion(
            boolean shouldMarkComplete,
            String messageSameState,
            String messageCompleteTaskSuccess
    ) throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (targetIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToComplete = lastShownList.get(targetIndex);

        try {
            Task completedTask = createTaskWithCompletion(taskToComplete, shouldMarkComplete, messageSameState);
            mementos.addUndoMementoAndClearRedo(new TaskMemento((Task) new Task(taskToComplete), completedTask));
            model.updateTask(targetIndex, completedTask);
        } catch (NoChangeException nce) {
            throw new CommandException(messageSameState);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(messageCompleteTaskSuccess, taskToComplete));
    }
    //@@author

    /**
     * Creates a new task with the given completion state
     * @param task the task to be completed/uncompleted
     * @param shouldMarkComplete whether to mark the task complete or uncomplete
     * @param messageSameState the error message if the task is already completed/uncompleted
     * @return a copy of the given {@code task} but with the completion set to {@code shouldMarkComplete}
     * @throws NoChangeException thrown if the task is already completed/uncompleted
     */
    protected static Task createTaskWithCompletion(
            ReadOnlyTask task,
            boolean shouldMarkComplete,
            String messageSameState
    ) throws NoChangeException {
        if (task.getComplete().getCompletion() == shouldMarkComplete) {
            throw new NoChangeException(messageSameState);
        }
        Description description = task.getDescription();
        UniqueTagList tags = task.getTags();
        Duration duration = task.getDuration();
        DueDate dueDate = task.getDueDate();
        Complete complete = new Complete(shouldMarkComplete);
        TaskId id = task.getTaskId();

        Task completedTask = new Task(description, dueDate, duration, tags, complete, id);

        return completedTask;
    }
}
