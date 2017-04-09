package seedu.task.logic.commands;

import java.util.logging.Logger;

import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the task manager.
 */
public class DeleteCommand extends Command {
    private static final Logger logger = LogsCenter.getLogger(DeleteCommand.class);

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_WORD_REC = "deletethis";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
    public static final String MESSAGE_MISSING_TASK = "The target task cannot be missing";
    public static final String MESSAGE_NULL_TIMING =
            "Both the start and end timings must be specified for a recurring task";

    public final int targetIndex;
    public final boolean isSpecific;

    public DeleteCommand(int targetIndex, boolean isSpecific) {
        this.targetIndex = targetIndex;
        this.isSpecific = isSpecific;
    }

    //@@author A0164212U
    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);
        Task deleteOccurrence = null;

        if (isSpecific && taskToDelete.getOccurrences().size() > 1) {
            Task taskToAdd = new Task(taskToDelete);
            try {
                deleteOccurrence = Task.extractOccurrence(taskToDelete);
                model.deleteThisTask(taskToDelete, taskToAdd);
                logger.info("Deleting specific instance of recurring task");
                return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, deleteOccurrence));
            } catch (DuplicateTaskException e) {
                throw new CommandException(AddCommand.MESSAGE_DUPLICATE_TASK);
            } catch (TaskNotFoundException tnfe) {
                assert false : MESSAGE_MISSING_TASK;
            } catch (IllegalValueException e) {
                throw new CommandException(MESSAGE_NULL_TIMING);
            }
        } else {
            try {
                model.deleteTask(taskToDelete);
            } catch (TaskNotFoundException tnfe) {
                assert false : MESSAGE_MISSING_TASK;
            }
        }
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
