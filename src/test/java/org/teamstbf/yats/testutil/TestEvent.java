package org.teamstbf.yats.testutil;

import org.teamstbf.yats.model.item.Date;
import org.teamstbf.yats.model.item.Description;
import org.teamstbf.yats.model.item.Location;
import org.teamstbf.yats.model.item.Periodic;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.Schedule;
import org.teamstbf.yats.model.item.Title;
import org.teamstbf.yats.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestEvent implements ReadOnlyEvent {

	private Title name;
	private Periodic period;
	private Schedule startTime;
	private Schedule endTime;
	private Description description;
	private boolean isDone;
	private Location location;
	private UniqueTagList tags;

	public TestEvent() {
		tags = new UniqueTagList();
	}

	/**
	 * Creates a copy of {@code personToCopy}.
	 */
	public TestEvent(TestEvent eventToCopy) {
		this.name = eventToCopy.getTitle();
		this.period = eventToCopy.getPeriod();
		this.location = eventToCopy.getLocation();
		this.startTime = eventToCopy.getStartTime();
		this.endTime = eventToCopy.getEndTime();
		this.description = eventToCopy.getDescription();
		this.tags = eventToCopy.getTags();
	}

	public String getAddCommand() {
		StringBuilder sb = new StringBuilder();
		sb.append("add " + this.getTitle().fullName + " ");
		sb.append("l/" + this.getLocation().value + " ");
		sb.append("p/" + this.getPeriod().value + " ");
		sb.append("s/" + this.getStartTime().value + " ");
		sb.append("e/" + this.getEndTime().value + " ");
		sb.append("d/" + this.getDescription().value + " ");
		this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
		return sb.toString();
	}

	@Override
	public Date getDeadline() {
		// TODO Auto-generated method stub
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
		return period;
	}

	@Override
	public Schedule getStartTime() {
		return startTime;
	}

	@Override
	public UniqueTagList getTags() {
		return tags;
	}

	@Override
	public Title getTitle() {
		return name;
	}

	public void setDescription(Description description) {
		this.description = description;
	}

	public void setEndTime(Schedule schedule) {
		this.endTime = schedule;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setPeriod(Periodic periodic) {
		this.period = periodic;
	}

	public void setStartTime(Schedule schedule) {
		this.startTime = schedule;
	}

	public void setTags(UniqueTagList tags) {
		this.tags = tags;
	}

	public void setTitle(Title name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return getAsText();
	}
}
