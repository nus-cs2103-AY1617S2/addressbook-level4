package seedu.address.model.person;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

public class Task implements ReadOnlyTask {

    private Description description;
    private Priority priority;
    private ByDate bydate;
    private ByTime bytime;
    private Location location;

    private UniqueTagList tags;

    /**
     * Description and Priority must be present.
     */
    public Task(Description description, Priority priority, ByDate bydate, ByTime bytime,
            Location location, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, priority, tags);
        this.description = description;
        this.priority = priority;
        this.bydate = bydate;
        this.bytime = bytime;
        this.location = location;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getPriority(), source.getByDate(),
                source.getByTime(), source.getLocation(), source.getTags());
    }

    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setByDate(ByDate bydate) {
        assert bydate != null;
        this.bydate = bydate;
    }

    @Override
    public ByDate getByDate() {
        return bydate;
    }

    public void setByTime(ByTime bytime) {
        //can be null
        this.bytime = bytime;
    }

    @Override
    public ByTime getByTime() {
        return bytime;
    }

    public void setLocation(Location location) {
        //can be null
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
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setDescription(replacement.getDescription());
        this.setByDate(replacement.getByDate());
        this.setByTime(replacement.getByTime());
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
        return Objects.hash(description, bydate, bytime, location, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
