package seedu.task.logic.commands;

import java.util.Arrays;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the task
 * manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
	    + ": Deletes the task identified by the index number used in the last task listing.\n"
	    + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";

    public final int[] targetIndex;

    public DeleteCommand(int[] targetIndex) {
	Arrays.sort(targetIndex);
	this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
	UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
	StringBuilder sb = new StringBuilder();
	for (int i = 0; i < targetIndex.length; i++) {

	    if (lastShownList.size() < targetIndex[i] - i) {
		throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
	    }

	    ReadOnlyTask taskToDelete = lastShownList.get(targetIndex[i] - 1 - i);
	    String deletedTaskName = taskToDelete.getTaskName().toString();
	    try {
		model.deleteTask(taskToDelete);
	    } catch (TaskNotFoundException pnfe) {
		assert false : "The target task cannot be missing";
	    }

	    sb.append(String.format(MESSAGE_DELETE_TASK_SUCCESS, deletedTaskName));
	    sb.append("\n");
	}
	return new CommandResult(sb.toString());
    }

    public boolean isUndoable() {
	return true;
    }

}
