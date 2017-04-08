package org.teamstbf.yats.logic.commands;

import java.util.List;

import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.IsDone;
import org.teamstbf.yats.model.item.ReadOnlyEvent;

// @@author A0139448U
/**
 *
 * Command to Mark an existing task as done in the task scheduler.
 */
public class MarkDoneCommand extends Command {

	public static final String COMMAND_WORD = "mark";
	public static final String MESSAGE_EDIT_TASK_SUCCESS = "Task marked as done: %1$s";
	public static final String MESSAGE_ALR_MARKED = "Task already marked as done.";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the task identified as done "
			+ "by the index number used in the last task listing. "
			+ "Parameters: INDEX (must be a positive integer) "
			+ "Example: " + COMMAND_WORD + " 1";

	public final int targetIndex;

	public MarkDoneCommand(int targetIndex) {
		assert targetIndex > 0;
		this.targetIndex = targetIndex - 1;
	}

	@Override
	public CommandResult execute() throws CommandException {

		List<ReadOnlyEvent> lastShownList = model.getFilteredTaskList();

		if (targetIndex >= lastShownList.size()) {
			throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}

		ReadOnlyEvent taskToMark = lastShownList.get(targetIndex);
		Event markedTask = new Event(taskToMark.getTitle(), taskToMark.getLocation(), taskToMark.getStartTime(), taskToMark.getEndTime(), taskToMark.getDeadline(), taskToMark.getDescription(), taskToMark.getTags(), new IsDone(), taskToMark.isRecurring(), taskToMark.getRecurrence());

		if (markedTask.getIsDone().getValue().equals(IsDone.ISDONE_DONE)) {
			return new CommandResult(MESSAGE_ALR_MARKED);
		}

		model.saveImageOfCurrentTaskManager(); // For undo command

		if (markedTask.isRecurring()) {
			markedTask.markDone();
		} else {
			markedTask.getIsDone().markDone();
		}

		model.updateEvent(targetIndex, markedTask);
		model.updateDoneTaskList();
		model.updateFilteredListToShowAll();
		markedTask.setPriority(0);
		return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToMark));
	}

}
