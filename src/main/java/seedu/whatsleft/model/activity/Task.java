package seedu.whatsleft.model.activity;

import java.util.Objects;

import seedu.whatsleft.commons.util.CollectionUtil;
import seedu.whatsleft.model.tag.UniqueTagList;
//@@author A0121668A
public class Task implements ReadOnlyTask {

    public static final boolean DEFAULT_TASK_STATUS = false;
    public static final boolean COMPLETED_TASK_STATUS = true;

    private Description description;
    private Priority priority;
    private ByTime byTime;
    private ByDate byDate;
    private Location location;
    private boolean status;

    private UniqueTagList tags;

    /**
     * Description and Priority must be present.
     */
    public Task(Description description, Priority priority, ByTime byTime, ByDate byDate,
            Location location, UniqueTagList tags, boolean status) {

        assert !CollectionUtil.isAnyNull(description, priority, tags);
        this.description = description;
        this.priority = priority;
        this.byTime = byTime;
        this.byDate = byDate;
        this.location = location;
        this.status = status;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
  //@@author

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getPriority(), source.getByTime(),
                source.getByDate(), source.getLocation(), source.getTags(), source.getStatus());
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

    public void setByTime(ByTime byTime) {
        this.byTime = byTime;
    }

    @Override
    public ByTime getByTime() {
        return byTime;
    }

    public void setByDate(ByDate byDate) {
        this.byDate = byDate;
    }

    @Override
    public ByDate getByDate() {
        return byDate;
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

    @Override
    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean hasDeadline() {
        if (!this.byTime.isExisting() && !this.byDate.isExisting()) {
            return false;
        } else {
            return true;
        }
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

    //@@author A0121668A
    /**
     * Mark a task as complete
     */

    public void completeTask() {
        this.status = true;
    }

    /**
     * Mark a task as pending
     */
    public void redoTask() {
        this.status = false;

    }
    //@@author A0121668A
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, byDate, byTime, location, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
