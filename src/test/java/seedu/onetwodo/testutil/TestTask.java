package seedu.onetwodo.testutil;

import seedu.onetwodo.model.tag.UniqueTagList;
import seedu.onetwodo.model.task.EndDate;
import seedu.onetwodo.model.task.Description;
import seedu.onetwodo.model.task.Name;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.StartDate;
import seedu.onetwodo.model.task.TaskType;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Description description;
    private EndDate endDate;
    private StartDate startDate;
    private UniqueTagList tags;
    private TaskType type;
    private boolean isDone = false;

    public TestTask() {
        tags = new UniqueTagList();
        type = TaskType.DEADLINE;
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.startDate = taskToCopy.getStartDate();
        this.endDate = taskToCopy.getEndDate();
        this.description = taskToCopy.getDescription();
        this.tags = taskToCopy.getTags();
        this.type = taskToCopy.getTaskType();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setEndDate(EndDate endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(StartDate startDate) {
        this.startDate = startDate;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public StartDate getStartDate() {
        return startDate;
    }

    @Override
    public EndDate getEndDate() {
        return endDate;
    }

    @Override
    public Description getDescription() {
        return description;
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
        sb.append("add " + this.getName().fullName + " ");
        sb.append("d/" + this.getDescription().value + " ");
        sb.append("s/" + this.getStartDate().value + " ");
        sb.append("e/" + this.getEndDate().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public TaskType getTaskType() {
        return type;
    }

    @Override
    public boolean getDoneStatus() {
        return isDone;
    }
}
