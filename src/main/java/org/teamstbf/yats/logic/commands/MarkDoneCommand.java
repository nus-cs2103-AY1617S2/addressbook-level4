package org.teamstbf.yats.logic.commands;

import java.util.List;

import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.IsDone;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.UniqueEventList;

/**
 *
 * Marks an existing task as done in the task scheduler.
 */
// @@author A0139448U
public class MarkDoneCommand extends Command {

	public final int targetIndex;

	public MarkDoneCommand(int targetIndex) {
		assert targetIndex > 0;
		this.targetIndex = targetIndex - 1;
	}

	public static final String COMMAND_WORD = "mark";
	public static final String MESSAGE_EDIT_TASK_SUCCESS = "Task marked as done: %1$s";
	public static final String MESSAGE_ALR_MARKED = "Task already marked as done.";
	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the task identified as done "
			+ "by the index number used in the last task listing. " + "Parameters: INDEX (must be a positive integer) "
			+ "Example: " + COMMAND_WORD + " 1";

	@Override
	public CommandResult execute() throws CommandException {

		List<ReadOnlyEvent> lastShownList = model.getFilteredTaskList();

		if (targetIndex >= lastShownList.size()) {
			throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}

		ReadOnlyEvent taskToMark = lastShownList.get(targetIndex);
		Event markedTask = new Event(taskToMark);
		if (markedTask.getIsDone().getValue().equals(IsDone.ISDONE_DONE)) {
			return new CommandResult(MESSAGE_ALR_MARKED);
		}
		try {
			markedTask.getIsDone().markDone();
			model.updateEvent(targetIndex, markedTask);
		} catch (UniqueEventList.DuplicateEventException dpe) {
			throw new CommandException(MESSAGE_DUPLICATE_TASK);
		}
		model.updateFilteredListToShowAll();
		markedTask.setPriority(0);
		return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToMark));
	}
}
