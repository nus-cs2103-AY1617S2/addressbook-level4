package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task in the endtime book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Title title;
    private StartTime starttime;
    private Venue venue;
    private EndTime endtime;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Title title, Venue venue, StartTime starttime, EndTime endtime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(title, starttime, venue, endtime, tags);
        this.title = title;
        this.venue = venue;
        this.starttime = starttime;
        this.endtime = endtime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTitle(), source.getVenue(), source.getStartTime(), source.getEndTime(), source.getTags());
    }

    public void setTitle(Title name) {
        assert name != null;
        this.title = name;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    public void setStartTime(StartTime starttime) {
        assert starttime != null;
        this.starttime = starttime;
    }

    @Override
    public StartTime getStartTime() {
        return starttime;
    }

    public void setVenue(Venue venue) {
        assert venue != null;
        this.venue = venue;
    }

    @Override
    public Venue getVenue() {
        return venue;
    }

    public void setEndTime(EndTime endtime) {
        assert endtime != null;
        this.endtime = endtime;
    }

    @Override
    public EndTime getEndTime() {
        return endtime;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
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
        return Objects.hash(title, venue, starttime, endtime, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
