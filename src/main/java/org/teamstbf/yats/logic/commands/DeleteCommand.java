package org.teamstbf.yats.logic.commands;

import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.commons.core.UnmodifiableObservableList;
import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.UniqueEventList.EventNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the task
 * manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
	    + ": Deletes the task identified by the index number used in the last task listing.\n"
	    + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final int targetIndex;

    public DeleteCommand(int targetIndex) {
	this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

	UnmodifiableObservableList<ReadOnlyEvent> lastShownList = model.getFilteredUndoneTaskList();

	if (lastShownList.size() < targetIndex) {
	    throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
	}

	ReadOnlyEvent taskToDelete = lastShownList.get(targetIndex - 1);

	try {
	    model.deleteEvent(taskToDelete);
	} catch (EventNotFoundException pnfe) {
	    assert false : "The target task cannot be missing";
	}

	return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
