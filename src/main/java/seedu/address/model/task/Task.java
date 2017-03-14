package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task in the endtime book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask, Comparable<Task>{

    private Title title;
    private StartTime startTime;
    private Venue venue;
    private EndTime endTime;
    private UrgencyLevel urgencyLevel;
    private Description description;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Title title, Venue venue, StartTime starttime, EndTime endtime, UrgencyLevel urgencylevel,Description description, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(title, starttime, venue, endtime, tags);
        this.title = title;
        this.venue = venue;
        this.startTime = starttime;
        this.endTime = endtime;
        this.urgencyLevel = urgencylevel;
        this.description = description;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTitle(), source.getVenue(), source.getStartTime(), source.getEndTime(), source.getUrgencyLevel(), source.getDescription(),source.getTags());
    }

    public void setTitle(Title name) {
        assert name != null;
        this.title = name;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    public void setStartTime(StartTime startTime) {
        assert startTime != null;
        this.startTime = startTime;
    }

    @Override
    public StartTime getStartTime() {
        return startTime;
    }

    public void setVenue(Venue venue) {
        assert venue != null;
        this.venue = venue;
    }

    @Override
    public Venue getVenue() {
        return venue;
    }

    public void setEndTime(EndTime endTime) {
        assert endTime != null;
        this.endTime = endTime;
    }

    @Override
    public EndTime getEndTime() {
        return endTime;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    @Override
    public UrgencyLevel getUrgencyLevel() {
        return urgencyLevel;
    }
    
    @Override
    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    public void setUrgencyLevel(UrgencyLevel urgencyLevel) {
        assert urgencyLevel != null;
        this.urgencyLevel = urgencyLevel;
    }

    /**
     * Replaces this Task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this Task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setTitle(replacement.getTitle());
        this.setStartTime(replacement.getStartTime());
        this.setVenue(replacement.getVenue());
        this.setEndTime(replacement.getEndTime());
        this.setUrgencyLevel(replacement.getUrgencyLevel());
        this.setDescription(replacement.getDescription());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, venue, startTime, endTime, urgencyLevel, description, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    @Override
    public int compareTo(Task task) {
        return this.getUrgencyLevel().getIntValue() - task.getUrgencyLevel().getIntValue();
    }

}
