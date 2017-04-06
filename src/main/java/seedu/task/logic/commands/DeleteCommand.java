package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the task manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_WORD_REC = "deletethis";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

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
<<<<<<< HEAD
            newTask = Task.extractOccurrence(taskToDelete);
=======
            Task taskToAdd = new Task(taskToDelete);
            deleteOccurrence = Task.modifyOccurrence(taskToDelete);
>>>>>>> 04f6f9c98b7537c17ec3539b02857921a1ffd5b7
            try {
                model.deleteThisTask(taskToDelete, taskToAdd);
                return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, deleteOccurrence));
            } catch (DuplicateTaskException e) {
                throw new CommandException(AddCommand.MESSAGE_DUPLICATE_TASK);
            } catch (TaskNotFoundException tnfe) {
                assert false : "The target task cannot be missing";
            }
        } else {
            try {
                model.deleteTask(taskToDelete);
            } catch (TaskNotFoundException tnfe) {
                assert false : "The target task cannot be missing";
            }
        }
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
