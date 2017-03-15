package seedu.tache.logic.commands;

import seedu.tache.commons.core.Messages;
import seedu.tache.commons.core.UnmodifiableObservableList;
import seedu.tache.logic.commands.exceptions.CommandException;
import seedu.tache.model.task.ReadOnlyDetailedTask;
import seedu.tache.model.task.ReadOnlyTask;
import seedu.tache.model.task.UniqueDetailedTaskList.DetailedTaskNotFoundException;
import seedu.tache.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the task manager.
 */
public class DeleteCommand extends Command {

    public enum TaskType { TypeTask, TypeDetailedTask }

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final int targetIndex;
    public final TaskType type;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        this.type = TaskType.TypeTask;
    }

    public DeleteCommand(int targetIndex, TaskType type) {
        this.targetIndex = targetIndex;
        this.type = type;
    }


    @Override
    public CommandResult execute() throws CommandException {

        if (type.equals(TaskType.TypeTask)) {
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

            if (lastShownList.size() < targetIndex) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

            try {
                model.deleteTask(taskToDelete);
            } catch (TaskNotFoundException tnfe) {
                assert false : "The target task cannot be missing";

            }

            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
        } else {
            UnmodifiableObservableList<ReadOnlyDetailedTask> lastShownList = model.getFilteredDetailedTaskList();

            if (lastShownList.size() < targetIndex) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyDetailedTask detailedTaskToDelete = lastShownList.get(targetIndex - 1);

            try {
                model.deleteDetailedTask(detailedTaskToDelete);
            } catch (DetailedTaskNotFoundException dtnfe) {
                assert false : "The target task cannot be missing";

            }

            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, detailedTaskToDelete));
        }
    }

}
