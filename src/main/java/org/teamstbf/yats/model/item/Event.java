package org.teamstbf.yats.model.item;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.commons.util.CollectionUtil;
import org.teamstbf.yats.model.tag.UniqueTagList;

public class Event implements ReadOnlyEvent {

    public static final String MESSAGE_TOO_MANY_TIME = "There should be at most 2 time points in the task.";
    public static final String MESSAGE_RECURRENCE_TIME_ERROR = "Recurring task should contain only one date.";
    public static final String MESSAGE_INVALID_TIME = "Invalid time slots.";
    public static final int SIZE_DEADLINE_TASK = 1;
    public static final int SIZE_EVENT_TASK = 2;
    public static final int SIZE_FLOATING_TASK = 0;
    public static final int SIZE_RECURRENCE_DATE = 1;
    public static final int INDEX_FIRST_DATE = 0;
    public static final int INDEX_SECOND_DATE = 1;
    public static final int INITIALPRIORITY = 1;

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
	if (parameters.get("recurrence") != null) {
	    this.isRecurring = true;
	    //recurring task has empty start end and deadline
	    this.startTime = new Schedule("");
	    this.endTime = new Schedule("");
	    this.deadline = new Schedule("");
	    fillRecurrence((List<Date>) parameters.get("time"), (String) parameters.get("recurrence"));
	} else {
	    this.isRecurring = false;
	    this.recurrence = new Recurrence();
	    fillStartEndDateAndDeadline(parameters);
	}
    }
    
    private void fillRecurrence(List<Date> dateList, String period) throws IllegalValueException {
        if (dateList.size() > SIZE_RECURRENCE_DATE) {
            throw new IllegalValueException(MESSAGE_INVALID_TIME);
        }
        this.recurrence = new Recurrence(dateList.get(INDEX_FIRST_DATE), period);
    }

    private void fillStartEndDateAndDeadline(HashMap<String, Object> parameters) throws IllegalValueException {
	// check number of time group, if>2, throws exception
	if (parameters.get("time") != null) {
	    List<Date> dateList = (List<Date>) parameters.get("time");
	    if (dateList.size() > SIZE_EVENT_TASK) {
		throw new IllegalValueException(MESSAGE_TOO_MANY_TIME);
	    } else if (dateList.size() == SIZE_EVENT_TASK) {
		// event task with start and end time
		this.startTime = new Schedule(dateList.get(INDEX_FIRST_DATE));
		this.endTime = new Schedule(dateList.get(INDEX_SECOND_DATE));
		this.deadline = new Schedule("");
	    } else if (dateList.size() == SIZE_DEADLINE_TASK) {
		// deadline task with only end time
		this.startTime = new Schedule("");
		this.endTime = new Schedule("");
		this.deadline = new Schedule(dateList.get(INDEX_FIRST_DATE));
	    } else {
		throw new IllegalValueException(MESSAGE_INVALID_TIME);
	    }
	} else {
	    // floating task with no time
	    this.startTime = new Schedule("");
	    this.endTime = new Schedule("");
	    this.deadline = new Schedule("");
	}
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
        this.tags = new UniqueTagList(editedReadOnlyEvent.getTags()); // protect internal tags from
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

    public boolean equals(Object other) {
	return other == this // short circuit if same object
		|| (other instanceof ReadOnlyEvent // instanceof handles nulls
			&& this.isSameStateAs((ReadOnlyEvent) other));
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
    public boolean hasStartEndTime() {
	if (this.startTime.toString().equals("") || this.startTime.toString().equals("")) {
	    return false;
	}
	return true;
    }

    @Override
    public boolean isRecurring() {
        return this.isRecurring;
    }

}
