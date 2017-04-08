package org.teamstbf.yats.logic.commands;

import java.util.List;
import java.util.Stack;

import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.IsDone;
import org.teamstbf.yats.model.item.ReadOnlyEvent;

/**
 * Batch marks existing tasks as done in the task scheduler.
 *
 */
// @@author A0138952W
public class BatchMarkDoneCommand extends Command {

	public static final String COMMAND_WORD = "mark";
	public static final String MESSAGE_EDIT_TASK_SUCCESS = "%d tasks marked as done";
	public static final String MESSAGE_ALR_MARKED = "Task already marked as done.";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the task identified as done "
			+ "by the index number used in the last task listing. " + "Parameters: INDEX (must be a positive integer) "
			+ "Example: " + COMMAND_WORD + " 1";

	public final Stack<Integer> targetIndexes;

	public BatchMarkDoneCommand(Stack<Integer> targetIndexes) {
		this.targetIndexes = targetIndexes;
	}

	@Override
	public CommandResult execute() throws CommandException {

		List<ReadOnlyEvent> lastShownList = model.getFilteredTaskList();
		int numOfTask = targetIndexes.size();

		if (lastShownList.size() < targetIndexes.peek()) {
			throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}
		model.saveImageOfCurrentTaskManager();

		for (int i = 0; i < numOfTask; i++) {
			ReadOnlyEvent taskToMark = lastShownList.get(targetIndexes.peek());

			Event markedTask = new Event(taskToMark.getTitle(), taskToMark.getLocation(), taskToMark.getStartTime(), taskToMark.getEndTime(), taskToMark.getDeadline(), taskToMark.getDescription(), taskToMark.getTags(), new IsDone(), taskToMark.isRecurring(), taskToMark.getRecurrence());

			if (markedTask.getIsDone().getValue().equals(IsDone.ISDONE_DONE)) {
				return new CommandResult(MESSAGE_ALR_MARKED);
			}
			markedTask.getIsDone().markDone();
			model.updateEvent(targetIndexes.pop(), markedTask);
			model.updateDoneTaskList();
			model.updateFilteredListToShowAll();
		}
		return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, numOfTask));
	}
}
