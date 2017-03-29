package org.teamstbf.yats.testutil;

import org.teamstbf.yats.model.item.Description;
import org.teamstbf.yats.model.item.IsDone;
import org.teamstbf.yats.model.item.Location;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.Schedule;
import org.teamstbf.yats.model.item.Title;
import org.teamstbf.yats.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestEvent implements ReadOnlyEvent {

	private Title name;
	private Schedule startTime;
	private Schedule endTime;
	private Description description;
	private IsDone isDone;
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
		this.location = eventToCopy.getLocation();
		this.startTime = eventToCopy.getStartTime();
		this.endTime = eventToCopy.getEndTime();
		this.description = eventToCopy.getDescription();
		this.tags = eventToCopy.getTags();
		this.isDone = eventToCopy.getIsDone();
	}

	public String getAddCommand() {
		StringBuilder sb = new StringBuilder();
		sb.append("add " + this.getTitle().fullName + " ");
		sb.append("l/" + this.getLocation().value + " ");
		sb.append("s/" + this.getStartTime().toString() + " ");
		sb.append("e/" + this.getEndTime().toString() + " ");
		sb.append("d/" + this.getDescription().value + " ");
		this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
		return sb.toString();
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

	public void setStartTime(Schedule schedule) {
		this.startTime = schedule;
	}

	public void setTags(UniqueTagList tags) {
		this.tags = tags;
	}

	public void setTitle(Title name) {
		this.name = name;
	}

	public void setIsDone(IsDone isDone) {
		this.isDone = isDone;
	}

	@Override
	public String toString() {
		return getAsText();
	}

	@Override
	public IsDone getIsDone() {
		return isDone;
	}

	@Override
	public void markDone() {
		isDone.markDone();
	}

}
