package seedu.doist.model.task;

import java.util.Date;
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
    private Date startDate;
    private Date endDate;

    /**
     * Every field must be present and not null.
     */
    public Task(Description name, Priority priority, FinishedStatus finishedStatus,
            UniqueTagList tags, Date startDate, Date endDate) {
        assert !CollectionUtil.isAnyNull(name, priority, finishedStatus, tags);
        this.desc = name;
        this.priority = priority;
        this.finishedStatus = finishedStatus;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Task(Description name, Priority priority, UniqueTagList tags) {
        this(name, priority, new FinishedStatus(), tags, null, null);
    }

    public Task(Description name, UniqueTagList tags) {
        this(name, new Priority(), new FinishedStatus(), tags, null, null);
    }

    public Task(Description name, Date startDate, Date endDate) {
        this(name, new Priority(), new FinishedStatus(), new UniqueTagList(), startDate, endDate);
    }

    public Task(Description name, UniqueTagList tags, Date startDate, Date endDate) {
        this(name, new Priority(), new FinishedStatus(), tags, startDate, endDate);
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getPriority(), source.getFinishedStatus(),
                source.getTags(), source.getStartDate(), source.getEndDate());
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

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setFinishedStatus(boolean isFinished) {
        assert finishedStatus != null;
        this.finishedStatus.setIsFinished(isFinished);
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
     * Replaces this person's tags with the tags in the argument tag list.
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
