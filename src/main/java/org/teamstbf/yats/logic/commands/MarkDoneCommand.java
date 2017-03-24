package org.teamstbf.yats.logic.commands;

import java.util.List;

import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.model.item.Description;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.IsDone;
import org.teamstbf.yats.model.item.Location;
import org.teamstbf.yats.model.item.Periodic;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.Schedule;
import org.teamstbf.yats.model.item.Title;
import org.teamstbf.yats.model.item.UniqueEventList;
import org.teamstbf.yats.model.tag.UniqueTagList;

/**
 *
 * Marks an existing task as done in the task scheduler.
 */
// @@author A0139448U
public class MarkDoneCommand extends EditCommand {

	public MarkDoneCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
		super(filteredTaskListIndex, editTaskDescriptor);
	}

	public static final String COMMAND_WORD = "done";
	public static final String MESSAGE_EDIT_TASK_SUCCESS = "Task marked as done: %1$s";
	public static final String MESSAGE_ALR_MARKED = "Task already marked as done.";
	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the task identified as done "
			+ "by the index number used in the last task listing. " + "Parameters: INDEX (must be a positive integer) "
			+ "Example: " + COMMAND_WORD + " 1";

	@Override
	public CommandResult execute() throws CommandException {
		List<ReadOnlyEvent> lastShownList = model.getFilteredTaskList();

		if (filteredTaskListIndex >= lastShownList.size()) {
			throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}
		ReadOnlyEvent taskToEdit = lastShownList.get(filteredTaskListIndex);
		if (taskToEdit.isTaskDone()) {
			return new CommandResult(MESSAGE_ALR_MARKED);
		}
		Event editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

		try {
			model.updateEvent(filteredTaskListIndex, editedTask);
		} catch (UniqueEventList.DuplicateEventException dpe) {
			throw new CommandException(MESSAGE_DUPLICATE_TASK);
		}
		model.updateFilteredListToShowAll();
		return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
	}

	/**
	 * Creates and returns a {@code Task} with the details of {@code taskToEdit}
	 * edited with {@code editTaskDescriptor}.
	 */

	protected static Event createEditedTask(ReadOnlyEvent taskToEdit, EditTaskDescriptor editTaskDescriptor) {
		assert taskToEdit != null;

		Title updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getTitle);
		Location updatedLocation = editTaskDescriptor.getLocation().orElseGet(taskToEdit::getLocation);
		Schedule updatedStartTime = editTaskDescriptor.getStartTime().orElseGet(taskToEdit::getStartTime);
		Schedule updatedEndTime = editTaskDescriptor.getEndTime().orElseGet(taskToEdit::getEndTime);
		Description updatedDescription = editTaskDescriptor.getDescription().orElseGet(taskToEdit::getDescription);
		UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);
		if (editTaskDescriptor.tags.isPresent() && updatedTags.isTagPresent()) {
			updatedTags.removeAndMerge(taskToEdit.getTags());
		}
		IsDone isDone = editTaskDescriptor.markDone();

		return new Event(updatedName, updatedLocation, updatedStartTime, updatedEndTime,
				updatedDescription, updatedTags, isDone);
	}

}
