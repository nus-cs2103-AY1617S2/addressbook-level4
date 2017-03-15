package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Description;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskDate;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

    private Description description;
    private TaskDate startDate;
    private TaskDate endDate;
    private Priority priority;
    private boolean complete;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestPerson(TestPerson personToCopy) {
        this.description = personToCopy.getDescription();
        this.priority = personToCopy.getPriority();
        this.startDate = personToCopy.getStartDate();
        this.endDate = personToCopy.getEndDate();
        this.tags = personToCopy.getTags();
        this.complete = false;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setStartDate(TaskDate taskDate) {
        this.startDate = taskDate;
    }

    public void setEndDate(TaskDate taskDate) {
        this.endDate = taskDate;
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

    @Override
    public boolean isComplete() {
        return complete;
    }
}
