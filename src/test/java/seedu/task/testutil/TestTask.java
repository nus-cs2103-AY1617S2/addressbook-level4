package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Date;
import seedu.task.model.task.Location;
import seedu.task.model.task.Name;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Remark;
import seedu.task.model.task.Task;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Location location;
    private Remark remark;
    private Date startDate;
    private Date endDate;
    private boolean isDone;
    private String eventId;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     *
     * @throws IllegalValueException
     *             when startDate is not strictly before endDate
     */
    public TestTask(TestTask taskToCopy) throws IllegalValueException {

        if (!checkDates(taskToCopy.getStartDate(), taskToCopy.getEndDate())) {
            throw new IllegalValueException(Task.MESSAGE_TASK_CONSTRAINTS);
        }
        this.name = taskToCopy.getName();
        this.startDate = taskToCopy.getStartDate();
        this.endDate = taskToCopy.getEndDate();
        this.remark = taskToCopy.getRemark();
        this.location = taskToCopy.getLocation();
        this.isDone = false;
        this.tags = taskToCopy.getTags();
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @return true if one of two dates is null. if both not null, only returns true if startDate is strictly before
     *         endDate
     */
    private boolean checkDates(Date startDate, Date endDate) {
        return (startDate.isNull() || endDate.isNull()) ? true
                : startDate.getDateValue().before(endDate.getDateValue());
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setRemark(Remark remark) {
        this.remark = remark;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public Remark getRemark() {
        return remark;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    // @@author A0140063X
    @Override
    public String getEventId() {
        return eventId;
    }

    // @@author
    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        // sequence name->location->start date->end date->remark->tags
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        if (this.getLocation() != null) {
            sb.append("l/" + this.getLocation().value + " ");
        }
        if (this.getStartDate() != null) {
            sb.append("s/" + this.getStartDate().toString() + " ");
        }
        if (this.getEndDate() != null) {
            sb.append("e/" + this.getEndDate().toString() + " ");
        }
        if (this.getRemark() != null) {
            sb.append("r/" + this.getRemark().value + " ");
        }
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public int compareTo(ReadOnlyTask o) {
        // Same end date then compare according to names lexicographically
        if ((this.getEndDate() == null && o.getEndDate() == null) || (this.getEndDate().equals(o.getEndDate()))) {
            return this.getName().fullName.compareTo(o.getName().fullName);
        } else {
            if (this.getEndDate().isNull())
                return 1;
            if (o.getEndDate().isNull())
                return -1;
            return (Date.isBefore(this.getEndDate(), o.getEndDate())) ? -1 : 1;
        }
    }
}
