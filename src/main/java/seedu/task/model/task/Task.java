package seedu.task.model.task;

import java.util.Objects;

import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Date startDate;
    private Date endDate;
    private Remark remark;
    private Location location;
    private boolean isDone;
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     * @param startDate TODO
     * @param isDone TODO
     */
    public Task(Name name, Date startDate, Date endDate, Remark remark,
        Location location, UniqueTagList tags, boolean isDone) {
        assert !CollectionUtil.isAnyNull(name, endDate, remark, location, tags);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remark = remark;
        this.location = location;
        this.isDone = isDone;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartDate(), source.getEndDate(), source.getRemark(),
                source.getLocation(), source.getTags(), source.isDone());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setStartDate(Date startDate) {
        assert startDate != null;
        this.startDate = startDate;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        assert endDate != null;
        this.endDate = endDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    public void setRemark(Remark remark) {
        assert remark != null;
        this.remark = remark;
    }

    @Override
    public Remark getRemark() {
        return remark;
    }

    public void setLocation(Location location) {
        assert location != null;
        this.location = location;
    }

    @Override
    public Location getLocation() {
        return location;
    }
    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public boolean isDone() {
        return isDone;
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
        this.setStartDate(replacement.getStartDate());
        this.setEndDate(replacement.getEndDate());
        this.setRemark(replacement.getRemark());
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
        return Objects.hash(name, endDate, remark, location, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
