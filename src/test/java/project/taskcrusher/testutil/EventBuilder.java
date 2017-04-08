package project.taskcrusher.testutil;

import java.util.ArrayList;
import java.util.List;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.model.event.Location;
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.shared.Name;
import project.taskcrusher.model.shared.Priority;
import project.taskcrusher.model.tag.Tag;
import project.taskcrusher.model.tag.UniqueTagList;

public class EventBuilder {

    private TestEventCard event;

    public EventBuilder() {
        this.event = new TestEventCard();
    }

    /**
     * Initializes the EventBuilder with the data of {@code taskToCopy}.
     */
    public EventBuilder(TestEventCard eventToCopy) {
        this.event = new TestEventCard(eventToCopy);
    }

    public EventBuilder withName(String name) throws IllegalValueException {
        this.event.setName(new Name(name));
        return this;
    }

    public EventBuilder withTags(String ... tags) throws IllegalValueException {
        event.setTags(new UniqueTagList());
        for (String tag: tags) {
            event.getTags().add(new Tag(tag));
        }
        return this;
    }

    public EventBuilder withDescription(String description) throws IllegalValueException {
        this.event.setDescription(new Description(description));
        return this;
    }

    public EventBuilder withPriority(String priority) throws IllegalValueException {
        this.event.setPriority(new Priority(priority));
        return this;
    }

    public EventBuilder withLocation(String location) throws IllegalValueException {
        this.event.setLocation(new Location(location));
        return this;
    }

    public EventBuilder withTimeslots(List<Timeslot> timeslots) throws IllegalValueException {
        List<Timeslot> timeslotCopy = new ArrayList<Timeslot>();
        timeslotCopy.addAll(timeslots);
        this.event.setTimeslots(timeslotCopy);
        return this;
    }

    public TestEventCard build() {
        return this.event;
    }


}
