package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task in the ToDo List.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Description description;
    private Priority priority;
    private Timing startTiming;
    private Timing endTiming;
    private boolean complete;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Description description, Priority priority, Timing startTiming, Timing endTiming, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, priority, startTiming, tags);
        this.description = description;
        this.priority = priority;
        this.startTiming = startTiming;
        this.endTiming = endTiming;
        this.complete = false;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getPriority(), source.getStartTiming(),
            source.getEndTiming(), source.getTags());
    }

    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    public void setPriority(Priority priority) {
        assert priority != null;
        this.priority = priority;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setStartTiming(Timing startTiming) {
        assert startTiming != null;
        this.startTiming = startTiming;
    }

    @Override
    public Timing getStartTiming() {
        return startTiming;
    }

    public void setEndTiming(Timing endTiming) {
        assert endTiming != null;
        this.endTiming = endTiming;
    }

    @Override
    public Timing getEndTiming() {
        return endTiming;
    }

    public void setComplete() {
        this.complete = true;
    }

    public boolean isComplete() {
        return this.complete;
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
        this.setPriority(replacement.getPriority());
        this.setStartTiming(replacement.getStartTiming());
        this.setEndTiming(replacement.getEndTiming());
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
        return Objects.hash(description, priority, startTiming, endTiming, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
