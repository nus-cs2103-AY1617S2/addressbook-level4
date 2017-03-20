package seedu.address.testutil;

import java.util.Optional;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Description;
import seedu.address.model.task.EndTime;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Title;
import seedu.address.model.task.UrgencyLevel;
import seedu.address.model.task.Venue;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Title title;
    private Optional<Venue> venue;
    private Optional<StartTime> starttime;
    private Optional<EndTime> endtime;
    private Optional<UrgencyLevel> urgencyLevel;
    private Optional<Description> description;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.title = taskToCopy.getTitle();
        this.venue = taskToCopy.getVenue();
        this.endtime = taskToCopy.getEndTime();
        this.urgencyLevel = taskToCopy.getUrgencyLevel();
        this.description = taskToCopy.getDescription();
        this.tags = taskToCopy.getTags();
    }
    //@@author A0122017Y
    public void setTitle(Title title) {
        this.title = title;
    }

    public void setEndTime(EndTime endtime) {
        this.endtime = Optional.of(endtime);
    }

    public void setVenue(Venue venue) {
        this.venue = Optional.of(venue);
    }

	public void setUrgencyLevel(UrgencyLevel urgencyLevel) {
		this.urgencyLevel = Optional.of(urgencyLevel);
	}

    public void setDescription(Description description) {
        this.description = Optional.of(description);
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Title getTitle() {
        return title;
    }
    
    @Override
    public Optional<StartTime> getStartTime() {
        return starttime;
    }
    
    @Override
    public Optional<EndTime> getEndTime() {
        return endtime;
    }

    @Override
    public Optional<Venue> getVenue() {
        return venue;
    }

    @Override
    public Optional<Description> getDescription() {
        return description;
    }

    @Override
    public Optional<UrgencyLevel> getUrgencyLevel() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTitle().title + " ");
        sb.append("@@" + this.getVenueString() + " ");
        sb.append("by:" + this.getEndTimeString() + " ");
        sb.append("**" + this.getUrgencyLevelString() + " ");
        sb.append("d:" + this.getDescriptionString() + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("##" + s.tagName + " "));
        return sb.toString();
    }
}
