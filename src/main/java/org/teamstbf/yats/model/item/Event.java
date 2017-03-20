package org.teamstbf.yats.model.item;

import java.util.HashMap;
import java.util.Objects;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.commons.util.CollectionUtil;
import org.teamstbf.yats.model.tag.UniqueTagList;

public class Event implements ReadOnlyEvent {

	private Title name;
	private Periodic period;
	private Schedule startTime;
	private Schedule endTime;
	private Description description;
	private boolean isDone;
	private Location location;
	private UniqueTagList tags;

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
		if (parameters.get("period") != null) {
			this.period = new Periodic((String) parameters.get("period"));
		} else {
			this.period = new Periodic("none");
		}
		if (parameters.get("location") != null) {
			this.location = new Location((String) parameters.get("location"));
		} else {
			this.location = new Location(" ");
		}
		if (parameters.get("start") != null) {
			this.startTime = new Schedule((String) parameters.get("start"));
		} else {
			this.startTime = new Schedule(" ");
		}
		if (parameters.get("end") != null) {
			this.endTime = new Schedule((String) parameters.get("end"));
		} else {
			this.endTime = new Schedule(" ");
		}
		if (parameters.get("description") != null) {
			this.description = new Description((String) parameters.get("description"));
		} else {
			this.description = new Description(" ");
		}
		this.isDone = false;
		this.tags = new UniqueTagList(tags);
	}

	public Event(ReadOnlyEvent editedReadOnlyPerson) {
		this(editedReadOnlyPerson.getTitle(), editedReadOnlyPerson.getLocation(), editedReadOnlyPerson.getPeriod(),
				editedReadOnlyPerson.getStartTime(), editedReadOnlyPerson.getEndTime(),
				editedReadOnlyPerson.getDescription(), editedReadOnlyPerson.getTags());
	}

	/**
	 *
	 * Every field must be present and not null.
	 *
	 */
	public Event(Title name, Location location, Periodic periodic, Schedule startTime, Schedule endTime,
			Description description, UniqueTagList tags) {
		assert !CollectionUtil.isAnyNull(name);
		this.name = name;
		this.period = periodic;
		this.location = location;
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
		this.isDone = false;
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

	public Date getDeadline() {
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

}
