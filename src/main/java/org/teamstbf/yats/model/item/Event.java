package org.teamstbf.yats.model.item;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.commons.util.CollectionUtil;
import org.teamstbf.yats.model.tag.UniqueTagList;

public class Event implements ReadOnlyEvent {

	public static final String MESSAGE_TOO_MANY_TIME = "There should be at most 2 time points in the task.";
	public static final String MESSAGE_RECURRENCE_TIME_ERROR = "Recurring task should contain at least one and only one date.";
	public static final String MESSAGE_INVALID_TIME = "Invalid time slots.";
	public static final int SIZE_RECURRENCE_DATE = 1;
	public static final int INITIALPRIORITY = 1;
	public static final int STEP_START_TIME = -2;
	public static final int STEP_END_TIME = 2;

	private Title name;
	private Schedule startTime;
	private Schedule endTime;
	private Schedule deadline;
	private Description description;
	private IsDone isDone;
	private Location location;
	private UniqueTagList tags;
	private Integer priority;
	private boolean isRecurring;
	private Recurrence recurrence;

	// @@author A0116219L
	/**
	 * Creates an Event object using map of parameters, only name is compulsory,
	 * others are optional
	 *
	 * @param map
	 *            of parameters
	 * @param tags
	 * @throws IllegalValueException
	 */

	public Event(HashMap<String, Object> parameters, UniqueTagList tags) throws IllegalValueException {
		assert !CollectionUtil.isAnyNull(parameters.get("name"));
		this.name = new Title((String) parameters.get("name"));
		// check optional parameters' existence
		if (parameters.get("location") != null) {
			this.location = new Location((String) parameters.get("location"));
		} else {
			this.location = new Location(" ");
		}
		if (parameters.get("description") != null) {
			this.description = new Description((String) parameters.get("description"));
		} else {
			this.description = new Description(" ");
		}
		this.isDone = new IsDone();
		this.tags = new UniqueTagList(tags);
		this.setPriority(1);
		// recurring task has start&end or a deadline
		fillStartEndDateAndDeadline(parameters);
		if (parameters.get("recurrence") != null) {
			this.isRecurring = true;
			fillRecurrence((String) parameters.get("recurrence"));
		} else {
			this.isRecurring = false;
			this.recurrence = new Recurrence();
		}
	}

	/**
	 *
	 * Every field must be present and not null.
	 *
	 */
	public Event(Title name, Location location, Schedule startTime, Schedule endTime, Schedule deadline,
			Description description, UniqueTagList tags, IsDone isDone) {
		assert !CollectionUtil.isAnyNull(name);
		this.name = name;
		this.location = location;
		this.startTime = startTime;
		this.endTime = endTime;
		this.deadline = deadline;
		this.description = description;
		this.isDone = isDone;
		this.tags = new UniqueTagList(tags); // protect internal tags from
		// changes in the arg list
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof ReadOnlyEvent // instanceof handles nulls
						&& this.isSameStateAs((ReadOnlyEvent) other));
	}

	private void fillRecurrence(String period) throws IllegalValueException {
		// get current date and time
		Date startDate = new Date();
		// recurring event task
		if (this.hasStartAndEndTime()) {
			startDate = this.startTime.getDate();
		}
		// recurring deadline task will take current date as starting date
		this.recurrence = new Recurrence(startDate, period);
	}

	private void fillStartEndDateAndDeadline(HashMap<String, Object> parameters) throws IllegalValueException {
		// set start and end if present
		if (parameters.get("start") != null) {
			this.startTime = new Schedule((Date) parameters.get("start"));
		} else {
			this.startTime = new Schedule("");
		}
		if (parameters.get("end") != null) {
			this.endTime = new Schedule((Date) parameters.get("end"));
		} else {
			this.endTime = new Schedule("");
		}
		// set deadline if present
		if (parameters.get("deadline") != null) {
			this.deadline = new Schedule((Date) parameters.get("deadline"));
		} else {
			this.deadline = new Schedule("");
		}
		// assign default end if only start is given
		if (parameters.get("start") != null && parameters.get("end") == null) {
			this.endTime = new Schedule(addHoursToDate(this.startTime.getDate(), STEP_END_TIME));
		}
		// assign default start if only end is given
		if (parameters.get("start") == null && parameters.get("end") != null) {
			this.startTime = new Schedule(addHoursToDate(this.endTime.getDate(), STEP_START_TIME));
		}
	}

	private Date addHoursToDate(Date date, int hours) {
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(date);
		tempCal.add(Calendar.HOUR_OF_DAY, hours);
		return tempCal.getTime();
	}

	public Event(ReadOnlyEvent editedReadOnlyEvent) {
		assert !CollectionUtil.isAnyNull(editedReadOnlyEvent.getTitle());
		this.name = editedReadOnlyEvent.getTitle();
		this.location = editedReadOnlyEvent.getLocation();
		this.startTime = editedReadOnlyEvent.getStartTime();
		this.endTime = editedReadOnlyEvent.getEndTime();
		this.deadline = editedReadOnlyEvent.getDeadline();
		this.description = editedReadOnlyEvent.getDescription();
		this.isDone = editedReadOnlyEvent.getIsDone();
		this.tags = new UniqueTagList(editedReadOnlyEvent.getTags()); // protect
		// internal
		// tags
		// from
		this.isRecurring = editedReadOnlyEvent.isRecurring();
		this.recurrence = editedReadOnlyEvent.getRecurrence();
	}

	/**
	 *
	 * Every field must be present and not null.
	 *
	 */
	public Event(Title name, Location location, Schedule startTime, Schedule endTime, Schedule deadline,
			Description description, UniqueTagList tags, IsDone isDone, boolean isRecurring, Recurrence recurrence) {
		assert !CollectionUtil.isAnyNull(name);
		this.name = name;
		this.location = location;
		this.startTime = startTime;
		this.endTime = endTime;
		this.deadline = deadline;
		this.description = description;
		this.isDone = isDone;
		this.tags = new UniqueTagList(tags); // protect internal tags from
		this.isRecurring = isRecurring;
		this.recurrence = recurrence;
		// changes in the arg list
	}

	@Override
	public Description getDescription() {
		return description;
	}

	@Override
	public Schedule getEndTime() {
		return endTime;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public Schedule getStartTime() {
		return startTime;
	}

	@Override
	public UniqueTagList getTags() {
		return new UniqueTagList(tags);
	}

	@Override
	public Title getTitle() {
		return name;
	}

	@Override
	public Recurrence getRecurrence() {
		return this.recurrence;
	}

	@Override
	public int hashCode() {
		// use this method for custom fields hashing instead of implementing
		// your own
		return Objects.hash(name, location, startTime, endTime, description, tags, recurrence);
	}

	/**
	 *
	 * Updates this person with the details of {@code replacement}.
	 *
	 */

	public void resetData(ReadOnlyEvent replacement) {
		assert replacement != null;
		this.setTitle(replacement.getTitle());
		this.setLocation(replacement.getLocation());
		this.setStartTime(replacement.getStartTime());
		this.setEndTime(replacement.getEndTime());
		this.setDeadline(replacement.getDeadline());
		this.setDescription(replacement.getDescription());
		this.setTags(replacement.getTags());
		this.setIsDone(replacement.getIsDone());
		this.isRecurring = replacement.isRecurring();
		this.recurrence = replacement.getRecurrence();
	}

	private void setIsDone(IsDone done) {
		this.isDone = done;
	}

	public void setDescription(Description description) {
		assert description != null;
		this.description = description;
	}

	public void setEndTime(Schedule schedule) {
		assert schedule != null;
		this.endTime = schedule;
	}

	public void setLocation(Location location) {
		assert location != null;
		this.location = location;
	}

	public void setStartTime(Schedule schedule) {
		assert schedule != null;
		this.startTime = schedule;
	}

	public void setDeadline(Schedule schedule) {
		assert schedule != null;
		this.deadline = schedule;
	}

	public void setRecurrence(Recurrence recurrence) {
		assert recurrence != null;
		this.recurrence = recurrence;
	}

	/**
	 *
	 * Replaces this person's tags with the tags in the argument tag list.
	 *
	 */
	public void setTags(UniqueTagList replacement) {
		tags.setTags(replacement);
	}

	public void setTitle(Title name) {
		assert name != null;
		this.name = name;
	}

	@Override
	public String toString() {
		return getAsText();
	}

	@Override
	public IsDone getIsDone() {
		return this.isDone;
	}

	@Override
	public void markDone() {
		if (this.isRecurring) {
			this.recurrence.markOccurenceDone();
		} else {
			this.isDone.markDone();
		}
	}

	@Override
	public Schedule getDeadline() {
		return this.deadline;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Override
	public boolean hasDeadline() {
		if (this.deadline.toString().equals("")) {
			return false;
		}
		return true;
	}

	@Override
	public boolean hasStartAndEndTime() {
		if (this.startTime.toString().equals("") || this.endTime.toString().equals("")) {
			return false;
		}
		return true;
	}

	@Override
	public boolean hasStartOrEndTime() {
		if (this.startTime.toString().equals("") && this.endTime.toString().equals("")) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isRecurring() {
		return this.isRecurring;
	}

}
