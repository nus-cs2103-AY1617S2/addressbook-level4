package seedu.doit.model.item;

import java.util.Objects;

import seedu.doit.commons.util.CollectionUtil;
import seedu.doit.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Priority priority;
    private StartTime startTime;
    private EndTime endTime;
    private Description description;
    private UniqueTagList tags;

    // ================ Constructor methods ==============================

    /**
     * Event Constructor where every field must be present and not null.
     */
    public Task(Name name, Priority priority, StartTime startTime, EndTime endTime,
                Description description, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, priority, startTime, endTime, description, tags);
        this.name = name;
        this.priority = priority;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Task Constructor every field must be present except for startTime.
     */
    public Task(Name name, Priority priority, EndTime endTime, Description description, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, priority, endTime, description, tags);
        this.name = name;
        this.priority = priority;
        this.startTime = null;
        this.endTime = endTime;
        this.description = description;
        this.tags = new UniqueTagList(tags); // protect internal tags from
                                             // changes in the arg list
    }

    /**
     * Task Constructor every field must be present except for startTime and endTime.
     */
    public Task(Name name, Priority priority, Description description, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, priority, description, tags);
        this.name = name;
        this.priority = priority;
        this.startTime = null;
        this.endTime = endTime;
        this.description = description;
        this.tags = new UniqueTagList(tags); // protect internal tags from
                                             // changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getPriority(), source.getDescription(), source.getTags());
        this.startTime = source.getStartTime();
        this.endTime = source.getEndTime();
    }

    // ================ Getter and Setter methods ==============================

    @Override
    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        assert priority != null;
        this.priority = priority;
    }

    @Override
    public StartTime getStartTime() {
        return startTime;
    }

    public void setStartTime(StartTime startTime) {
        assert startTime != null;
        this.startTime = startTime;
    }

    @Override
    public EndTime getEndTime() {
        return endTime;
    }

    public void setEndTime(EndTime endTime) {
        assert endTime != null;
        this.endTime = endTime;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    @Override
    public boolean hasStartTime() {
        if (startTime != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasEndTime() {
        if (endTime != null) {
            return true;
        }
        return false;
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

        this.setName(replacement.getName());
        this.setPriority(replacement.getPriority());
        this.setEndTime(replacement.getEndTime());
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
        return Objects.hash(name, priority, endTime, description, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
