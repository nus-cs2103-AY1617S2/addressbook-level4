package seedu.doist.testutil;

import java.util.Date;

import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.Description;
import seedu.doist.model.task.FinishedStatus;
import seedu.doist.model.task.Priority;
import seedu.doist.model.task.ReadOnlyTask;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Description desc;
    private Priority priority;
    private FinishedStatus finishedStatus;
    private UniqueTagList tags;
    private Date startDate;
    private Date endDate;

    public TestTask() {
        tags = new UniqueTagList();
        finishedStatus = new FinishedStatus();
        priority = new Priority();
        startDate = endDate = null;
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.desc = taskToCopy.getDescription();
        this.priority = taskToCopy.getPriority();
        this.finishedStatus = taskToCopy.getFinishedStatus();
        this.tags = taskToCopy.getTags();
        this.startDate = taskToCopy.getStartDate();
        this.endDate = taskToCopy.getEndDate();
    }

    public void setName(Description desc) {
        this.desc = desc;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setFinishedStatus(boolean isFinished) {
        this.finishedStatus.setIsFinished(isFinished);
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public Description getDescription() {
        return desc;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public FinishedStatus getFinishedStatus() {
        return finishedStatus;
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
    public String toString() {
        return getAsText();
    }

    /**Function that returns true if this task is overdue i.e, not finished and past it's deadline **/
    public boolean isOverdue() {
        if (this.getFinishedStatus().getIsFinished()) {
            return false;
        } else if (this.getStartDate() != null && this.getEndDate() != null) {
            Date currentDate = new Date();
            return (this.getEndDate().compareTo(currentDate) < 0);
        } else {
            return false;
        }
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getDescription().desc + " ");
        sb.append("\\as " + this.getPriority().toString());
        if (!this.getTags().isEmpty()) {
            sb.append("\\under");
            this.getTags().asObservableList().stream().forEach(s -> sb.append(" " + s.tagName));
        }
        if (this.getStartDate() != null && this.getEndDate() != null) {
            sb.append("\\from " + this.getStartDate().toString() + " \\to " + this.getEndDate().toString());
        }
        return sb.toString();
    }
}
