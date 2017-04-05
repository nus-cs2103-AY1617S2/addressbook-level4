package org.teamstbf.yats.logic.commands;

import static org.teamstbf.yats.model.item.Event.INDEX_FIRST_DATE;
import static org.teamstbf.yats.model.item.Event.INDEX_SECOND_DATE;
import static org.teamstbf.yats.model.item.Event.MESSAGE_TOO_MANY_TIME;
import static org.teamstbf.yats.model.item.Event.SIZE_DEADLINE_TASK;
import static org.teamstbf.yats.model.item.Event.SIZE_EVENT_TASK;
import static org.teamstbf.yats.model.item.Event.SIZE_FLOATING_TASK;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.commons.util.CollectionUtil;
import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.model.item.Description;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.IsDone;
import org.teamstbf.yats.model.item.Location;
import org.teamstbf.yats.model.item.Periodic;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.Recurrence;
import org.teamstbf.yats.model.item.Schedule;
import org.teamstbf.yats.model.item.Title;
import org.teamstbf.yats.model.item.UniqueEventList;
import org.teamstbf.yats.model.tag.UniqueTagList;

/**
 * Edits the details of an existing task in the task scheduler.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
	    + "by the index number used in the last task listing. "
	    + "Existing values will be overwritten by the input values.\n"
	    + "Parameters: INDEX (must be a positive integer) [, START to END TIME] [//DESCRIPTION] [#TAGS]...\n"
	    + "Example: " + COMMAND_WORD + " 1 s/10:00am,10/10/2017 e/5:00pm,10/10/2017 d/lots of work to do t/school";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    protected final int filteredTaskListIndex;
    protected final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param filteredTaskListIndex
     *            the index of the task in the filtered task list to edit
     * @param editTaskDescriptor
     *            details to edit the task
     */
    public EditCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
	assert filteredTaskListIndex > 0;
	assert editTaskDescriptor != null;

	// converts filteredTaskListIndex from one-based to zero-based.
	this.filteredTaskListIndex = filteredTaskListIndex - 1;

	this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
	List<ReadOnlyEvent> lastShownList = model.getFilteredTaskList();

	if (filteredTaskListIndex >= lastShownList.size()) {
	    throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
	}

	ReadOnlyEvent taskToEdit = lastShownList.get(filteredTaskListIndex);
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
	Schedule updatedDeadline = editTaskDescriptor.getDeadline().orElseGet(taskToEdit::getDeadline);
	Description updatedDescription = editTaskDescriptor.getDescription().orElseGet(taskToEdit::getDescription);
	UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);
	if (editTaskDescriptor.tags.isPresent() && updatedTags.isTagPresent()) {
	    updatedTags.removeAndMerge(taskToEdit.getTags());
	}
	IsDone isDone = taskToEdit.getIsDone();
	//these fields are not editable therefore unchanged
	boolean isRecurring = taskToEdit.isRecurring();
	Recurrence recurrence = taskToEdit.getRecurrence();

	return new Event(updatedName, updatedLocation, updatedStartTime, updatedEndTime, updatedDeadline,
		updatedDescription, updatedTags, isDone, isRecurring, recurrence);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will
     * replace the corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
	private Optional<Title> name = Optional.empty();
	private Optional<Location> location = Optional.empty();
	private Optional<Schedule> deadline = Optional.empty();
	private Optional<Schedule> startTime = Optional.empty();
	private Optional<Schedule> endTime = Optional.empty();
	private Optional<Description> description = Optional.empty();
	private Optional<Periodic> periodic = Optional.empty();
	Optional<UniqueTagList> tags = Optional.empty();
	private IsDone isDone = new IsDone();

	public EditTaskDescriptor() {
	}

	public EditTaskDescriptor(EditTaskDescriptor toCopy) {
	    this.name = toCopy.getName();
	    this.location = toCopy.getLocation();
	    this.deadline = toCopy.getDeadline();
	    this.startTime = toCopy.getStartTime();
	    this.endTime = toCopy.getEndTime();
	    this.description = toCopy.getDescription();
	    this.periodic = toCopy.getPeriodic();
	    this.tags = toCopy.getTags();
	    this.isDone = toCopy.getIsDone();
	}

	/**
	 * Returns true if at least one field is edited.
	 */
	public boolean isAnyFieldEdited() {
	    return CollectionUtil.isAnyPresent(this.name, this.location, this.startTime, this.endTime, this.description,
		    this.periodic, this.tags);
	}

	public void setName(Optional<Title> name) {
	    assert name != null;
	    this.name = name;
	}

	public Optional<Title> getName() {
	    return name;
	}

	public void setLocation(Optional<Location> location) {
	    assert location != null;
	    this.location = location;
	}

	public Optional<Location> getLocation() {
	    return location;
	}

	/*
	 * Sets start and end time together for event, both start and end time
	 * must present, i.e.times.get().size() is 2
	 */
	public void setTime(Optional<List<Date>> timeList) throws IllegalValueException {
	    assert timeList != null;
	    // Optional is not necessary, natty always returns a List, even when
	    // it is empty

	    if (!timeList.isPresent()) {
		this.startTime = Optional.empty();
		this.endTime = Optional.empty();
		return;
	    }

	    List<Date> times = timeList.get();

	    if (times.size() > SIZE_EVENT_TASK) {
		throw new IllegalValueException(MESSAGE_TOO_MANY_TIME);
	    } else if (times.size() == SIZE_EVENT_TASK) {
		this.startTime = Optional.of(new Schedule(timeList.get().get(INDEX_FIRST_DATE)));
		this.endTime = Optional.of(new Schedule(timeList.get().get(INDEX_SECOND_DATE)));
		this.deadline = Optional.empty();
	    } else if (times.size() == SIZE_DEADLINE_TASK) {
		this.startTime = Optional.empty();
		this.endTime = Optional.empty();
		this.deadline = Optional.of(new Schedule(timeList.get().get(INDEX_FIRST_DATE)));
	    } else if (times.size() == SIZE_FLOATING_TASK) {
		this.startTime = Optional.empty();
		this.endTime = Optional.empty();
		this.deadline = Optional.empty();
	    } else {
		throw new IllegalValueException(null);
	    }
	}

	public Optional<Schedule> getStartTime() {
	    return startTime;
	}

	public Optional<Schedule> getEndTime() {
	    return endTime;
	}

	public void setPeriodic(Optional<Periodic> periodic) {
	    assert periodic != null;
	    this.periodic = periodic;
	}

	public Optional<Periodic> getPeriodic() {
	    return periodic;
	}

	public void setDescription(Optional<Description> description) {
	    assert description != null;
	    this.description = description;
	}

	public Optional<Description> getDescription() {
	    return description;
	}

	public Optional<Schedule> getDeadline() {
	    return deadline;
	}

	public void setTags(Optional<UniqueTagList> tags) {
	    assert tags != null;
	    this.tags = tags;
	}

	public Optional<UniqueTagList> getTags() {
	    return tags;
	}

	public IsDone getIsDone() {
	    return isDone;
	}

	public IsDone markDone() {
	    isDone.markDone();
	    return isDone;
	}
    }

}
