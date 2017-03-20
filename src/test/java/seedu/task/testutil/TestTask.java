package seedu.task.testutil;

import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Description;
import seedu.task.model.task.Priority;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Timing;

/**
 * A mutable Task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Description description;
    private Priority priority;
    private Timing startDate;
    private Timing endDate;
    private boolean complete;

    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.description = taskToCopy.getDescription();
        this.priority = taskToCopy.getPriority();
        this.startDate = taskToCopy.getStartTiming();
        this.endDate = taskToCopy.getEndTiming();
        this.tags = taskToCopy.getTags();
        this.complete = false;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setStartDate(Timing startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Timing endDate) {
        this.endDate = endDate;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public Timing getStartTiming() {
        return startDate;
    }

    @Override
    public Timing getEndTiming() {
        return endDate;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getDescription().description + " ");
        sb.append("p/" + this.getPriority().value + " ");
        sb.append("sd/" + this.getStartTiming().value + " ");
        sb.append("ed/" + this.getEndTiming().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public boolean isComplete() {
        return this.complete;
    }

}
