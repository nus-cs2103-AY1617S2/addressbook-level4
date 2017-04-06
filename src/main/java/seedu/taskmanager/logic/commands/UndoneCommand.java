package seedu.taskmanager.logic.commands;

import java.util.Optional;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.commons.core.UnmodifiableObservableList;
import seedu.taskmanager.logic.commands.exceptions.CommandException;
import seedu.taskmanager.model.tag.UniqueTagList;
import seedu.taskmanager.model.task.Description;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.Status;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.Title;
import seedu.taskmanager.model.task.UniqueTaskList;

// @@author A0114269E
/**
 * Marks a task as Incomplete in the Task Manager
 */
public class UndoneCommand extends Command {
    public static final String COMMAND_WORD = "undone";
    public static final String ALTERNATIVE_COMMAND_WORD = "revert";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark the task identified by the index number used in the last task listing as incomplete.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " or " + ALTERNATIVE_COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_UNDONE_TASK_SUCCESS = "Task successfully "
            + "marked Incomplete:\n%1$s";
    public static final String MESSAGE_MARK_UNDONE_TASK_FAILURE = "ERROR! Task is already "
            + "marked Incomplete.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    public final int targetIndex;

    public UndoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMarkUndone = lastShownList.get(targetIndex - 1);
        if (taskToMarkUndone.getStatus().value) {
            Task markedUndoneTask = createDoneTask(taskToMarkUndone);
            try {
                model.updateTask(targetIndex - 1, markedUndoneTask);
            } catch (UniqueTaskList.DuplicateTaskException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            }
        } else {
            return new CommandResult(String.format(MESSAGE_MARK_UNDONE_TASK_FAILURE, taskToMarkUndone));
        }
        model.updateFilteredListToShowAll();

        return new CommandResult(String.format(MESSAGE_MARK_UNDONE_TASK_SUCCESS, taskToMarkUndone));
    }

    /**
     * Creates and returns a {@code Task} with the status marked done
     */
    private static Task createDoneTask(ReadOnlyTask taskToMarkUndone) {
        assert taskToMarkUndone != null;

        Title updatedTitle = taskToMarkUndone.getTitle();
        Optional <StartDate> updatedStartDate = taskToMarkUndone.getStartDate();
        Optional <EndDate> updatedEndDate = taskToMarkUndone.getEndDate();
        Optional <Description> updatedDescription = taskToMarkUndone.getDescription();
        Status updatedStatus = new Status(false);
        UniqueTagList updatedTags = taskToMarkUndone.getTags();

        return new Task(updatedTitle, updatedStartDate, updatedEndDate, updatedDescription, updatedStatus,
                updatedTags);
    }
}
// @@author
