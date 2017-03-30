package org.teamstbf.yats.testutil;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.model.item.Description;
import org.teamstbf.yats.model.item.IsDone;
import org.teamstbf.yats.model.item.Location;
import org.teamstbf.yats.model.item.Schedule;
import org.teamstbf.yats.model.item.Title;
import org.teamstbf.yats.model.tag.Tag;
import org.teamstbf.yats.model.tag.UniqueTagList;

/**
 *
 */
public class EventBuilder {

	private TestEvent task;

	public EventBuilder() {
		this.task = new TestEvent();
	}

	/**
	 * Initializes the PersonBuilder with the data of {@code personToCopy}.
	 */
	public EventBuilder(TestEvent taskToCopy) {
		this.task = new TestEvent(taskToCopy);
	}

	public TestEvent build() {
		return this.task;
	}

	public EventBuilder withDescription(String address) throws IllegalValueException {
		this.task.setDescription(new Description(address));
		return this;
	}

	public EventBuilder withEndTime(String timing) throws IllegalValueException {
		this.task.setEndTime(new Schedule(timing));
		return this;
	}

	public EventBuilder withLocation(String location) throws IllegalValueException {
		this.task.setLocation(new Location(location));
		return this;
	}

	public EventBuilder withStartTime(String timing) throws IllegalValueException {
		this.task.setStartTime(new Schedule(timing));
		return this;
	}

	public EventBuilder withTags(String... tags) throws IllegalValueException {
		task.setTags(new UniqueTagList());
		for (String tag : tags) {
			task.getTags().add(new Tag(tag));
		}
		return this;
	}

	public EventBuilder withTitle(String title) throws IllegalValueException {
		this.task.setTitle(new Title(title));
		return this;
	}

	public EventBuilder withIsDone(String email) throws IllegalValueException {
		this.task.setIsDone(new IsDone(email));
		return this;
	}

	public EventBuilder withDeadline(String string) {
		this.task.setDeadline(new Schedule(string));
		return this;
	}
}
