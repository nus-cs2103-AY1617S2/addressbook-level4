package todolist.testutil;

import java.util.Optional;

import todolist.model.tag.UniqueTagList;
import todolist.model.task.Description;
import todolist.model.task.EndTime;
import todolist.model.task.ReadOnlyTask;
import todolist.model.task.StartTime;
import todolist.model.task.Title;
import todolist.model.task.UrgencyLevel;
import todolist.model.task.Venue;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    public static final char FLOAT_CHAR = 'f';
    public static final char DEADLINE_CHAR = 'd';
    public static final char EVENT_CHAR = 'e';

    private Title title;
    private Optional<Venue> venue;
    private Optional<StartTime> starttime;
    private Optional<EndTime> endtime;
    private Optional<UrgencyLevel> urgencyLevel;
    private Optional<Description> description;
    private UniqueTagList tags;
    private String category;

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
        this.category = taskToCopy.getTaskCategory();
    }
    //@@author A0122017Y
    public void setTitle(Title title) {
        this.title = title;
    }

    public void setStartTime(StartTime starttime) {
        this.starttime = Optional.of(starttime);
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
    public String getTaskCategory() {
        return category;
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
        return urgencyLevel;
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
        sb.append("place/" + this.getVenueString() + " ");
        sb.append("from/" + this.getStartTimeString() + " ");
        sb.append("to/" + this.getEndTimeString() + " ");
        sb.append("level/" + this.getUrgencyLevelString() + " ");
        sb.append("des/" + this.getDescriptionString() + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("#" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public Character getTaskChar() {
        if (starttime.isPresent()) {
            return EVENT_CHAR;
        } else if (endtime.isPresent()) {
            return DEADLINE_CHAR;
        } else {
            return FLOAT_CHAR;
        }
    }
}
