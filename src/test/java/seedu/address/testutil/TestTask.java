package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Description;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;

/**
 * A mutable Task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Description description;
    private Priority priority;
    private TaskDate startDate;
    private TaskDate endDate;

    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code TaskToCopy}.
     */
    public TestTask(TestTask TaskToCopy) {
        this.description = TaskToCopy.getDescription();
        this.priority = TaskToCopy.getPriority();
        this.startDate = TaskToCopy.getStartDate();
        this.endDate = TaskToCopy.getEndDate();
        this.tags = TaskToCopy.getTags();
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setStartDate(TaskDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(TaskDate endDate) {
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
    public TaskDate getStartDate() {
        return startDate;
    }

    @Override
    public TaskDate getEndDate() {
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
        sb.append("sd/" + this.getStartDate().value + " ");
        sb.append("ed/" + this.getEndDate().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

}
