package seedu.task.model.task;

import java.util.Objects;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask, Comparable<ReadOnlyTask> {

    public static final String MESSAGE_TASK_CONSTRAINTS =
            "Start date/time must be earlier than end date/time";

    private Name name;
    private Date startDate;
    private Date endDate;
    private Remark remark;
    private Location location;
    private boolean isDone;
    private UniqueTagList tags;

    /**
     * Name field must be present and not null.
     * @throws IllegalValueException
     */
    public Task(Name name, Date startDate, Date endDate, Remark remark,
        Location location, UniqueTagList tags, boolean isDone) throws IllegalValueException {
        assert !CollectionUtil.isAnyNull(name, startDate, endDate, remark, location, tags);

        if (!isValidDates(startDate, endDate)) {
            throw new IllegalValueException(MESSAGE_TASK_CONSTRAINTS);
        }

        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remark = remark;
        this.location = location;
        this.isDone = isDone;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /***
     * *
     * @param startDate
     * @param endDate
     * @return true if one of two dates is null. if both not null, only returns
     *         true if startDate is strictly before endDate
     */
    private boolean isValidDates(Date startDate, Date endDate) {
        return (startDate.isNull() || endDate.isNull()) ? true
                : startDate.getDateValue().before(endDate.getDateValue());
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     * @throws IllegalValueException
     */
    public Task(ReadOnlyTask source) throws IllegalValueException {
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

    //@@author A0140063X
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    //@@author A0140063X
    @Override
    public Date getStartDate() {
        return startDate;
    }

    //@@author A0140063X
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    //@@author A0140063X
    @Override
    public Date getEndDate() {
        return endDate;
    }

    //@@author
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

    //@@author A0142487Y
    @Override
    public int compareTo(ReadOnlyTask o) {
        //Same end date then compare according to names lexicographically
        if ((this.getEndDate() == null && o.getEndDate() == null)
                || (this.getEndDate().equals(o.getEndDate()))) {
            return this.getName().fullName.compareTo(o.getName().fullName);
        } else {
            if (this.getEndDate().isNull()) return 1;
            if (o.getEndDate().isNull()) return -1;
            return (Date.isBefore(this.getEndDate(), o.getEndDate())) ? -1 : 1;
        }
    }
}
