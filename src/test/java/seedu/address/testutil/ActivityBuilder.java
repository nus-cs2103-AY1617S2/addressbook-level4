package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Description;
import seedu.address.model.person.EndDate;
import seedu.address.model.person.EndTime;
import seedu.address.model.person.Location;
import seedu.address.model.person.Priority;
import seedu.address.model.person.StartDate;
import seedu.address.model.person.StartTime;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 *
 */
public class ActivityBuilder {

    private TestActivity event;

    public ActivityBuilder() {
        this.event = new TestActivity();
    }

    /**
     * Initializes the ActivityBuilder with the data of {@code activityToCopy}.
     */
    public ActivityBuilder(TestActivity eventToCopy) {
        this.event = new TestActivity(eventToCopy);
    }

    public ActivityBuilder withDescription(String description) throws IllegalValueException {
        this.event.setDescription(new Description(description));
        return this;
    }

    public ActivityBuilder withTags(String ... tags) throws IllegalValueException {
        event.setTags(new UniqueTagList());
        for (String tag: tags) {
            event.getTags().add(new Tag(tag));
        }
        return this;
    }

    public ActivityBuilder withLocation(String location) throws IllegalValueException {
        this.event.setLocation(new Location(location));
        return this;
    }

    public ActivityBuilder withStartTime(String starttime) throws IllegalValueException {
        this.event.setStartTime(new StartTime(starttime));
        return this;
    }
    
    public ActivityBuilder withStartDate(String startdate) throws IllegalValueException {
        this.event.setStartDate(new StartDate(startdate));
        return this;
    }
    
    public ActivityBuilder withEndTime(String endtime) throws IllegalValueException {
        this.event.setEndTime(new EndTime(endtime));
        return this;
    }

    public ActivityBuilder withEndDate(String enddate) throws IllegalValueException {
        this.event.setEndDate(new EndDate(enddate));
        return this;
    }
    
    public TestActivity build() {
        return this.event;
    }

}
