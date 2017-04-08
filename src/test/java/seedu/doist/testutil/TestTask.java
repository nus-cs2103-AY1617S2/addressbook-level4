package seedu.doist.testutil;

import java.util.Date;

import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.Description;
import seedu.doist.model.task.FinishedStatus;
import seedu.doist.model.task.Priority;
import seedu.doist.model.task.ReadOnlyTask;
import seedu.doist.model.task.TaskDate;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Description desc;
    private Priority priority;
    private FinishedStatus finishedStatus;
    private UniqueTagList tags;
    private TaskDate dates;

    public TestTask() {
        tags = new UniqueTagList();
        finishedStatus = new FinishedStatus();
        priority = new Priority();
        dates = new TaskDate();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.desc = taskToCopy.getDescription();
        this.priority = taskToCopy.getPriority();
        this.finishedStatus = taskToCopy.getFinishedStatus();
        this.tags = taskToCopy.getTags();
        this.dates = taskToCopy.getDates();
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

    public void setDates(TaskDate dates) {
        this.dates = dates;
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
    public TaskDate getDates() {
        return dates;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    /**Function that returns true if this task is overdue i.e, not finished and past it's deadline **/
    public boolean isOverdue() {
        if (this.getFinishedStatus().getIsFinished()) {
            return false;
        } else if (this.getDates().getStartDate() != null && this.getDates().getEndDate() != null) {
            Date currentDate = new Date();
            return (this.getDates().getEndDate().compareTo(currentDate) < 0);
        } else {
            return false;
        }
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getDescription().desc + " ");
        sb.append("\\as " + this.getPriority().toString());
        //TODO: add dates to getAddCommand
        //if (!this.getEndDate().)
        if (!this.getTags().isEmpty()) {
            sb.append("\\under");
            this.getTags().asObservableList().stream().forEach(s -> sb.append(" " + s.tagName));
        }
        if (this.getDates().getStartDate() != null && this.getDates().getEndDate() != null) {
            sb.append("\\from " + this.getDates().getStartDate().toString() + " \\to " +
                    this.getDates().getEndDate().toString());
        }
        return sb.toString();
    }
}
