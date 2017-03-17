package seedu.doit.model.item;

import java.util.Objects;

import seedu.doit.commons.util.CollectionUtil;
import seedu.doit.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Task implements ReadOnlyTask, Comparable<Task> {

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
    public Task(Name name, Priority priority, StartTime startTime, EndTime endTime, Description description,
            UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, priority, startTime, endTime, description, tags);
        this.name = name;
        this.priority = priority;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.tags = new UniqueTagList(tags); // protect internal tags from
                                             // changes in the arg list
    }

    /**
     * Task Constructor every field must be present except for startTime.
     */
    public Task(Name name, Priority priority, EndTime endTime, Description description, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, priority, endTime, description, tags);
        this.name = name;
        this.priority = priority;
        this.startTime = new StartTime();
        this.endTime = endTime;
        this.description = description;
        this.tags = new UniqueTagList(tags); // protect internal tags from
                                             // changes in the arg list
    }

    /**
     * Task Constructor every field must be present except for startTime and
     * endTime.
     */
    public Task(Name name, Priority priority, Description description, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, priority, description, tags);
        this.name = name;
        this.priority = priority;
        this.startTime = new StartTime();
        this.endTime = new EndTime();
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
        return this.name;
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Priority getPriority() {
        return this.priority;
    }

    public void setPriority(Priority priority) {
        assert priority != null;
        this.priority = priority;
    }

    @Override
    public StartTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(StartTime startTime) {
        assert startTime != null;
        this.startTime = startTime;
    }

    @Override
    public EndTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(EndTime endTime) {
        assert endTime != null;
        this.endTime = endTime;
    }

    @Override
    public Description getDescription() {
        return this.description;
    }

    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    @Override
    public boolean hasStartTime() {
        if (this.startTime.value != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasEndTime() {
        if (this.endTime.value != null) {
            return true;
        }
        return false;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(this.tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        this.tags.setTags(replacement);
    }

    /**
     * Indicates if this item is an event
     */
    public boolean isEvent() {
        return (hasStartTime() && hasEndTime());
    }

    /**
     * Indicates if this item is a floatingTask
     */
    public boolean isFloatingTask() {
        return (!hasStartTime() && !hasEndTime());
    }

    /**
     * Indicates if this item is a task
     */
    public boolean isTask() {
        return (!hasStartTime() && hasEndTime());
    }

    // ================ Misc methods ==============================

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
        return (other == this // short circuit if same object
        ) || ((other instanceof ReadOnlyTask // instanceof handles nulls
        ) && this.isSameStateAs((ReadOnlyTask) other));
    }

    /**
     * Compares the current task with another Task other. The current task is
     * considered to be less than the other task if 1) This item has a earlier
     * start time associated 2) both items are not events but this item has a
     * later end time 3) but this task has a lexicographically smaller name
     * (useful when sorting tasks in testing)
     */
    @Override
    public int compareTo(Task other) {
        int comparedStartTime = compareStartTime(other);
        if (comparedStartTime != 0) {
            return comparedStartTime;
        }

        int comparedEndTime = compareEndTime(other);
        if (comparedEndTime != 0) {
            return comparedEndTime;
        }

        return compareName(other);
    }

    private int compareName(Task other) {
        return this.getName().toString().compareTo(other.getName().toString());
    }

    public int compareStartTime(Task other) {
        if (this.hasStartTime() && other.hasStartTime()) {
            return compareName(other);
        } else if (this.hasStartTime()) {
            return 1;
        } else if (other.hasStartTime()) {
            return -1;
        } else {
            return 0;
        }
    }

    public int compareEndTime(Task other) {
        if (this.hasEndTime() && other.hasEndTime()) {
            return compareName(other);
        } else if (this.hasEndTime()) {
            return -1;
        } else if (other.hasEndTime()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(this.name, this.priority, this.endTime, this.description, this.tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
