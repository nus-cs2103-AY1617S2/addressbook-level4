package seedu.geekeep.model.task;

import java.util.Objects;

import seedu.geekeep.commons.util.CollectionUtil;
import seedu.geekeep.model.tag.UniqueTagList;

/**
 * Represents a Task in the Task Manager. Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Title title;
    private EndDateTime endDateTime;
    private StartDateTime startDateTime;
    private Location location;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Title title, StartDateTime startDateTime,
                EndDateTime endDateTime, Location location, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(title, endDateTime, startDateTime, location, tags);
        this.title = title;
        this.endDateTime = endDateTime;
        this.startDateTime = startDateTime;
        this.location = location;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTitle(), source.getStartDateTime(),
             source.getEndDateTime(), source.getLocation(), source.getTags());
    }

    public void setTitle(Title title) {
        assert title != null;
        this.title = title;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    public void setEndDateTime(EndDateTime endDateTime) {
        assert endDateTime != null;
        this.endDateTime = endDateTime;
    }

    @Override
    public EndDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setStartDateTime(StartDateTime startDateTime) {
        assert startDateTime != null;
        this.startDateTime = startDateTime;
    }

    @Override
    public StartDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setLocation(Location location) {
        assert location != null;
        this.location = location;
    }

    @Override
    public Location getLocation() {
        return location;
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
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setTitle(replacement.getTitle());
        this.setEndDateTime(replacement.getEndDateTime());
        this.setStartDateTime(replacement.getStartDateTime());
        this.setLocation(replacement.getLocation());
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
        return Objects.hash(title, endDateTime, startDateTime, location, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
