package seedu.onetwodo.logic.commands;

import javafx.collections.transformation.FilteredList;
import seedu.onetwodo.commons.core.Messages;
import seedu.onetwodo.logic.commands.exceptions.CommandException;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the todo list.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive prefix integer)\n"
            + "Example: " + COMMAND_WORD + " t1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted %1$s";

    public final TaskType taskType;
    public final int targetIndex;

    public DeleteCommand(char taskType, int targetIndex) {
        this.taskType = TaskType.getTaskTypeFromChar(taskType);
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {
        FilteredList<ReadOnlyTask> lastShownList = model.getFilteredByDoneFindType(taskType);

        if (lastShownList.size() < targetIndex || taskType == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
