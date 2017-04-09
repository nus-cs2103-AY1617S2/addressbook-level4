package seedu.whatsleft.model.activity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.commons.util.CollectionUtil;
import seedu.whatsleft.model.tag.UniqueTagList;

//@@author A0121668A
/**
 * Represents a task(deadline) in WhatsLeft
 *
 */
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

    //@@author A0148038A
    /**
     * Create a task in WhatsLeft
     * Description and priority must be present and not null.
     * @param Description, Priority, ByTime, ByDate, Location, UniqueTagList
     * and a boolean variable that indicates task status
     * @throws IllegalValueException
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

    //@@author A0121668A
    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getPriority(), source.getByTime(),
                source.getByDate(), source.getLocation(), source.getTags(), source.getStatus());
    }

    public void setDescription(Description description) {
        assert description != null; //description must be present
        this.description = description;
    }
    //@@authorA0121668A
    @Override
    public Description getDescription() {
        return description;
    }

    public void setPriority(Priority priority) {
        assert priority != null; //priority must be present
        this.priority = priority;
    }

    @Override
    public Priority getPriority() {
        return priority;
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

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean getStatus() {
        return status;
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

    //@@author A0148038A
    @Override
    public String toString() {
        return getAsText();
    }

    /**
     * get description shown in the card in task list panel
     *
     * @return a string that represents description of the task
     */
    @Override
    public String getDescriptionToShow() {
        return getDescription().toString();
    }

    /**
     * get priority shown in the card in task list panel
     *
     * @return a string that represents priority of the task
     */
    @Override
    public String getPriorityToShow() {
        return "Priority: " + getPriority().toString().toUpperCase();
    }

    /**
     * get ByTimeDate(deadline) shown in the card in task list panel
     *
     * @return a string that represents deadline of the task
     */
    @Override
    public String getByTimeDateToShow() {
        if (hasDeadline()) {
            return "BY " + this.byTime.toString() + " " + this.byDate.toString();
        } else {
            return null;
        }
    }

    /**
     * get location shown in the card in task list panel
     *
     * @return a string that represents location of the task
     */
    @Override
    public String getLocationToShow() {
        if (getLocation().toString() != null) {
            return "@" + getLocation().toString();
        } else {
            return null;
        }

    }

    /**
     * get tags shown in the card in task list panel
     *
     * @return a list of strings that represents tags of the task
     */
    @Override
    public List<String> getTagsToShow() {
        return tags
                .asObservableList()
                .stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList());
    }
}
