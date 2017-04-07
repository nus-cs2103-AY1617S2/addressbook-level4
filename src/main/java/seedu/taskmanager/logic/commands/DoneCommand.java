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
 * Mark a task as done in the Task Manager
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";
    public static final String ALTERNATIVE_COMMAND_WORD_1 = "completed";
    public static final String ALTERNATIVE_COMMAND_WORD_2 = "finished";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark the task identified by the index number used in the last task listing as done.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " or "
            + ALTERNATIVE_COMMAND_WORD_1 + " or " + ALTERNATIVE_COMMAND_WORD_2 + " 1";

    public static final String MESSAGE_MARK_DONE_TASK_SUCCESS = "Task marked done: %1$s";
    public static final String MESSAGE_MARK_DONE_TASK_FAILURE = "ERROR! Task is already done.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    public final int targetIndex;

    public DoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getSelectedTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMarkDone = lastShownList.get(targetIndex - 1);
        if (!taskToMarkDone.getStatus().value) {
            Task markedDoneTask = createDoneTask(taskToMarkDone);
            try {
                model.updateTask(targetIndex - 1, markedDoneTask);
            } catch (UniqueTaskList.DuplicateTaskException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            }
        } else {
            return new CommandResult(String.format(MESSAGE_MARK_DONE_TASK_FAILURE, taskToMarkDone));
        }

        model.updateFilteredListToShowAll();

        return new CommandResult(String.format(MESSAGE_MARK_DONE_TASK_SUCCESS, taskToMarkDone));
    }

    /**
     * Creates and returns a {@code Task} with the status marked done
     */
    private static Task createDoneTask(ReadOnlyTask taskToMarkDone) {
        assert taskToMarkDone != null;

        Title updatedTitle = taskToMarkDone.getTitle();
        Optional<StartDate> updatedStartDate = taskToMarkDone.getStartDate();
        Optional<EndDate> updatedEndDate = taskToMarkDone.getEndDate();
        Optional<Description> updatedDescription = taskToMarkDone.getDescription();
        Status updatedStatus = new Status(true);
        UniqueTagList updatedTags = taskToMarkDone.getTags();

        return new Task(updatedTitle, updatedStartDate, updatedEndDate, updatedDescription, updatedStatus, updatedTags);
    }
}
// @@author
