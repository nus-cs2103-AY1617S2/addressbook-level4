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

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getDescription().desc + " ");
        sb.append("\\as " + this.getPriority().toString());
        if (!this.getTags().isEmpty()) {
            sb.append("\\under");
            this.getTags().asObservableList().stream().forEach(s -> sb.append(" " + s.tagName));
        }
        return sb.toString();
    }
}
