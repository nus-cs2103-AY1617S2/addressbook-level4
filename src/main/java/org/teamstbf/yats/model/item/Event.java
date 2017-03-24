package org.teamstbf.yats.model.item;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.commons.util.CollectionUtil;
import org.teamstbf.yats.model.tag.UniqueTagList;
import static org.teamstbf.yats.model.item.IsDone.ISDONE_NOTDONE;

public class Event implements ReadOnlyEvent {

    public static final String MESSAGE_TOO_MANY_TIME = "There should be at most 2 time points in the task.";
    public static final String MESSAGE_INVALID_TIME = "Invalid time slots.";
    public static final int SIZE_DEADLINE_TASK = 1;
    public static final int SIZE_EVENT_TASK = 2;
    public static final int SIZE_FLOATING_TASK = 0;
    public static final int INDEX_FIRST_DATE = 0;
    public static final int INDEX_SECOND_DATE = 1;
    
    private Title name;
    private Schedule startTime;
    private Schedule endTime;
    private Description description;
    private IsDone isDone;
    private Location location;
    private UniqueTagList tags;
    
    private Periodic period;

	public Event() {

	}

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
        this.isDone = new IsDone(ISDONE_NOTDONE);
        this.period = new Periodic("none");
        this.tags = new UniqueTagList(tags);
        
        //check number of time group, if>2, throws exception
        if (parameters.get("time") != null) {
            List<Date> dateList = (List<Date>) parameters.get("time");
            if (dateList.size() > SIZE_EVENT_TASK) {
                throw new IllegalValueException(MESSAGE_TOO_MANY_TIME);
            } else if (dateList.size() == SIZE_EVENT_TASK) {
                //event task with start and end time
                this.startTime = new Schedule(dateList.get(INDEX_FIRST_DATE));
                this.endTime = new Schedule(dateList.get(INDEX_SECOND_DATE));
            } else if (dateList.size() == SIZE_DEADLINE_TASK) {
                //deadline task with only end time
                this.startTime = new Schedule("");
                this.endTime = new Schedule(dateList.get(INDEX_FIRST_DATE));
            } else {
                throw new IllegalValueException(MESSAGE_INVALID_TIME);
            }
        } else {
            //floating task with no time
            this.startTime = new Schedule("");
            this.endTime = new Schedule("");
        }
	}

	public Event(ReadOnlyEvent editedReadOnlyEvent) {
		this(editedReadOnlyEvent.getTitle(), editedReadOnlyEvent.getLocation(), editedReadOnlyEvent.getPeriod(),
				editedReadOnlyEvent.getStartTime(), editedReadOnlyEvent.getEndTime(),
				editedReadOnlyEvent.getDescription(), editedReadOnlyEvent.getTags(), editedReadOnlyEvent.getIsDone());
	}

	/**
	 *
	 * Every field must be present and not null.
	 *
	 */
	public Event(Title name, Location location, Periodic periodic, Schedule startTime, Schedule endTime,
			Description description, UniqueTagList tags, IsDone isDone) {
		assert !CollectionUtil.isAnyNull(name);
		this.name = name;
		this.period = periodic;
		this.location = location;
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
		this.isDone = isDone;
		this.tags = new UniqueTagList(tags); // protect internal tags from
		// changes in the arg list
	}

	@Override

	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof ReadOnlyItem // instanceof handles nulls
						&& this.isSameStateAs((ReadOnlyEvent) other));
	}

	@Override

	public SimpleDate getDeadline() {
		return null;
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

	public Periodic getPeriod() {
		return this.period;
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

	public int hashCode() {
		// use this method for custom fields hashing instead of implementing
		// your own
		return Objects.hash(name, location, period, startTime, endTime, description, tags);
	}

	/**
	 *
	 * Updates this person with the details of {@code replacement}.
	 *
	 */

	public void resetData(ReadOnlyEvent replacement) {
		assert replacement != null;
		this.setTitle(replacement.getTitle());
		this.setPeriod(replacement.getPeriod());
		this.setLocation(replacement.getLocation());
		this.setStartTime(replacement.getStartTime());
		this.setEndTime(replacement.getEndTime());
		this.setDescription(replacement.getDescription());
		this.setTags(replacement.getTags());
		this.setIsDone(replacement.getIsDone());
	}

	private void setIsDone(IsDone done) {
		this.isDone = done;
	}

	public void setDescription(Description description) {
		assert description != null;
		this.description = description;
	}

	public void setEndTime(Schedule schedule) {
		assert endTime != null;
		this.endTime = schedule;
	}

	public void setLocation(Location location) {
		assert location != null;
		this.location = location;
	}

	public void setPeriod(Periodic period) {
		assert period != null;
		this.period = period;
	}

	public void setStartTime(Schedule schedule) {
		assert startTime != null;
		this.startTime = schedule;
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
	public boolean isTaskDone() {
		return this.isDone.getIsDone();
	}

	@Override
	public void markDone() {
		this.isDone.markDone();
	}

}
