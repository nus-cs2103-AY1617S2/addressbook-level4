package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Description;
import seedu.address.model.person.EndDate;
import seedu.address.model.person.EndTime;
import seedu.address.model.person.Location;
import seedu.address.model.person.StartDate;
import seedu.address.model.person.StartTime;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Event builder for GUI test
 */
public class EventBuilder {

    private TestEvent event;

    public EventBuilder() {
        this.event = new TestEvent();
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(TestEvent eventToCopy) {
        this.event = new TestEvent(eventToCopy);
    }

    public EventBuilder withDescription(String description) throws IllegalValueException {
        this.event.setDescription(new Description(description));
        return this;
    }

    public EventBuilder withStartTime(String starttime) throws IllegalValueException {
        this.event.setStartTime(new StartTime(starttime));
        return this;
    }

    public EventBuilder withStartDate(String startdate) throws IllegalValueException {
        this.event.setStartDate(new StartDate(startdate));
        return this;
    }

    public EventBuilder withEndTime(String endtime) throws IllegalValueException {
        this.event.setEndTime(new EndTime(endtime));
        return this;
    }

    public EventBuilder withEndDate(String enddate) throws IllegalValueException {
        this.event.setEndDate(new EndDate(enddate));
        return this;
    }

    public EventBuilder withLocation(String location) throws IllegalValueException {
        this.event.setLocation(new Location(location));
        return this;
    }

    public TestEvent build() {
        return this.event;
    }

    public EventBuilder withTags(String ... tags) throws IllegalValueException {
        event.setTags(new UniqueTagList());
        for (String tag: tags) {
            event.getTags().add(new Tag(tag));
        }
        return this;
    }

}
