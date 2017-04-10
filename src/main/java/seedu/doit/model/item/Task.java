// @@author A0139399J
package seedu.doit.model.item;

import java.util.Objects;

import seedu.doit.commons.util.CollectionUtil;
import seedu.doit.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Priority priority;
    private StartTime startTime;
    private EndTime endTime;
    private Description description;
    private boolean isDone;
    private UniqueTagList tags;

    // ================ Constructor methods ==============================

    /**
     * Event Constructor where every field must be present and not null.
     */
    public Task(Name name, Priority priority, StartTime startTime, EndTime endTime, Description description,
            UniqueTagList tags, boolean isDone) {
        assert !CollectionUtil.isAnyNull(name, startTime, endTime);
        this.name = name;
        this.priority = priority;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.isDone = isDone;
        this.tags = new UniqueTagList(tags); // protect internal tags from
                                             // changes in the arg list
    }

    /**
     * Event Constructor where every field must be present except isDone.
     */
    public Task(Name name, Priority priority, StartTime startTime, EndTime endTime, Description description,
            UniqueTagList tags) {
        this(name, priority, startTime, endTime, description, tags, false);
    }

    /**
     * Task Constructor every field must be present except for startTime and
     * isDone.
     */
    public Task(Name name, Priority priority, EndTime endTime, Description description, UniqueTagList tags,
            boolean isDone) {
        this(name, priority, new StartTime(), endTime, description, tags, isDone);
    }

    /**
     * Task Constructor every field must be present except for startTime.
     */
    public Task(Name name, Priority priority, EndTime endTime, Description description, UniqueTagList tags) {
        this(name, priority, new StartTime(), endTime, description, tags);
        assert !CollectionUtil.isAnyNull(name, endTime);
    }

    /**
     * FloatingTask Constructor every field must be present except for
     * startTime, endTime and isDone.
     */
    public Task(Name name, Priority priority, Description description, UniqueTagList tags, boolean isDone) {
        this(name, priority, new StartTime(), new EndTime(), description, tags, isDone);
    }

    /**
     * FloatingTask Constructor every field must be present except for startTime
     * and endTime.
     */
    public Task(Name name, Priority priority, Description description, UniqueTagList tags) {
        this(name, priority, new StartTime(), new EndTime(), description, tags);
        assert !CollectionUtil.isAnyNull(name);
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getPriority(), source.getDescription(), source.getTags());
        this.startTime = source.getStartTime();
        this.endTime = source.getDeadline();
        this.isDone = source.getIsDone();

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
    public boolean getIsDone() {
        return this.isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
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
    public EndTime getDeadline() {
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
        if (this.startTime == null) {
            return false;
        } else if (this.startTime.value.equals("")) {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasEndTime() {
        if (this.endTime == null) {
            return false;
        } else if (this.endTime.value.equals("")) {
            return false;
        }
        return true;
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
    @Override
    public boolean isEvent() {
        return (hasStartTime());
    }

    /**
     * Indicates if this item is a floatingTask
     */
    @Override
    public boolean isFloatingTask() {
        return (!hasStartTime() && !hasEndTime());
    }

    /**
     * Indicates if this item is a task
     */
    @Override
    public boolean isTask() {
        return (!hasStartTime() && hasEndTime());
    }

    /**
     * Returns 1 for task
     * 2 for event
     * 3 for floating tasks
     */
    @Override
    public int getItemType() {
        if (isTask()) {
            return 1;
        } else if (isEvent()) {
            return 2;
        } else {
            return 3;
        }
    }

    // ================ Misc methods ==============================

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setPriority(replacement.getPriority());
        this.setStartTime(replacement.getStartTime());
        this.setEndTime(replacement.getDeadline());
        this.setIsDone(replacement.getIsDone());
        this.setDescription(replacement.getDescription());
        this.setTags(replacement.getTags());
    }


    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(this.name, this.priority, this.description, this.tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public boolean equals(Object other) {
        return (other == this // short circuit if same object
                    ) || ((other instanceof ReadOnlyTask // instanceof handles nulls
                    ) && this.isSameStateAs((ReadOnlyTask) other));
    }

}
