package seedu.doist.model.task;

import java.util.Objects;

import seedu.doist.commons.util.CollectionUtil;
import seedu.doist.model.tag.UniqueTagList;

/**
 * Represents a Task in the to-do list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Description desc;
    private Priority priority;
    private FinishedStatus finishedStatus;
    private UniqueTagList tags;
    private TaskDate dates;

    /**
     * Every field must be present and not null.
     */
    public Task(Description name, Priority priority, FinishedStatus finishedStatus, TaskDate dates,
            UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, priority, finishedStatus, dates, tags);
        this.desc = name;
        this.priority = priority;
        this.finishedStatus = finishedStatus;
        this.dates = dates;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    public Task(Description name, Priority priority, UniqueTagList tags) {
        this(name, priority, new FinishedStatus(), new TaskDate(), tags);
    }

    public Task(Description name, UniqueTagList tags) {
        this(name, new Priority(), new FinishedStatus(), new TaskDate(), tags);
    }

    public Task(Description name, TaskDate dates, UniqueTagList tags) {
        this(name, new Priority(), new FinishedStatus(), dates, tags);
    }

    public Task(Description name, Priority priority, TaskDate dates, UniqueTagList tags) {
        this(name, priority, new FinishedStatus(), dates, tags);
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getPriority(), source.getFinishedStatus(),
                source.getDates(), source.getTags());
    }

    public void setDescription(Description desc) {
        assert desc != null;
        this.desc = desc;
    }

    @Override
    public Description getDescription() {
        return desc;
    }

    public void setPriority(Priority priority) {
        assert priority != null;
        this.priority = priority;
    }

    public void setDates(TaskDate dates) {
        this.dates = dates;
    }

    public TaskDate getDates() {
        return dates;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setFinishedStatus(FinishedStatus status) {
        assert finishedStatus != null;
        this.finishedStatus = status;
    }

    @Override
    public FinishedStatus getFinishedStatus() {
        return finishedStatus;
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
        this.setFinishedStatus(replacement.getFinishedStatus());
        this.setTags(replacement.getTags());
        this.setDates(replacement.getDates());
    }

    /**Function that returns true if this task is overdue i.e, not finished and past it's deadline **/
    public boolean isOverdue() {
        if (this.getFinishedStatus().getIsFinished()) {
            return false;
        } else {
            return this.getDates().isPast();
        }
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
        return Objects.hash(desc, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
