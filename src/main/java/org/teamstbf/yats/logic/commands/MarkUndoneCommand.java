package org.teamstbf.yats.logic.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.IsDone;
import org.teamstbf.yats.model.item.ReadOnlyEvent;

// @@author A0139448U
/**
 *
 * Command to Mark an existing task as not done in the task scheduler.
 */
public class MarkUndoneCommand extends Command {

	public static final String COMMAND_WORD = "unmark";
	public static final String MESSAGE_EDIT_TASK_SUCCESS = "Task marked as not done: %1$s";
	public static final String MESSAGE_ALR_MARKED = "Task is already marked as not done.";
	public static final String MESSAGE_NO_DONE_OCCURENCE = "Recurring task has no done occurrence.";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the task identified as not done "
			+ "by the index number used in the last task listing. " + "Parameters: INDEX (must be a positive integer) "
			+ "Example: " + COMMAND_WORD + " 1";

	private static final String TASK_DONE_IDENTIFIER = "Yes";

	public final int targetIndex;

	public MarkUndoneCommand(int targetIndex) {
		assert targetIndex > 0;
		this.targetIndex = targetIndex - 1;
	}

	@Override
	public CommandResult execute() throws CommandException {
		List<ReadOnlyEvent> lastShownList = retrieveDoneTaskList();

		if (targetIndex >= lastShownList.size()) {
			throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}

		ReadOnlyEvent taskToMark = lastShownList.get(targetIndex);
		Event markedTask = new Event(taskToMark.getTitle(), taskToMark.getLocation(), taskToMark.getStartTime(), taskToMark.getEndTime(), taskToMark.getDeadline(), taskToMark.getDescription(), taskToMark.getTags(), new IsDone("Yes"), taskToMark.isRecurring(), taskToMark.getRecurrence());

		model.saveImageOfCurrentTaskManager(); // For undo command

		if (markedTask.isRecurring()) {
			if (markedTask.getRecurrence().hasDoneOccurence()) {
				markedTask.getRecurrence().markOccurenceUndone();
			} else {
				return new CommandResult(MESSAGE_ALR_MARKED);
			}
		} else {
			if (markedTask.getIsDone().getValue().equals(IsDone.ISDONE_NOTDONE)) {
				return new CommandResult(MESSAGE_ALR_MARKED);
			}
			markedTask.getIsDone().markUndone();
		}

		model.updateEvent(targetIndex, markedTask);
		model.updateDoneTaskList();
		model.updateFilteredListToShowAll();
		markedTask.setPriority(1);
		return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToMark));
	}

	/*
	 * Returns a filtered task list of done tasks to be
	 * displayed in done task list on right of UI
	 */
	private List<ReadOnlyEvent> retrieveDoneTaskList() {
		Set<String> doneTaskIdentifier = new HashSet<String>();
		doneTaskIdentifier.add(TASK_DONE_IDENTIFIER);
		model.updateFilteredListToShowDone(doneTaskIdentifier);
		return model.getFilteredTaskList();
	}

}
