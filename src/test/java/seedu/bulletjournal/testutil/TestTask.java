package seedu.bulletjournal.testutil;

import seedu.bulletjournal.model.tag.UniqueTagList;
import seedu.bulletjournal.model.task.StartDate;
import seedu.bulletjournal.model.task.DueDate;
import seedu.bulletjournal.model.task.ReadOnlyTask;
import seedu.bulletjournal.model.task.Status;
import seedu.bulletjournal.model.task.TaskName;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private TaskName taskName;
    private StartDate startDate;
    private Status status;
    private DueDate dueDate;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestTask(TestTask personToCopy) {
        this.taskName = personToCopy.getName();
        this.dueDate = personToCopy.getPhone();
        this.status = personToCopy.getEmail();
        this.startDate = personToCopy.getAddress();
        this.tags = personToCopy.getTags();
    }

    public void setName(TaskName taskName) {
        this.taskName = taskName;
    }

    public void setAddress(StartDate startDate) {
        this.startDate = startDate;
    }

    public void setEmail(Status status) {
        this.status = status;
    }

    public void setPhone(DueDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public TaskName getName() {
        return taskName;
    }

    @Override
    public DueDate getPhone() {
        return dueDate;
    }

    @Override
    public Status getEmail() {
        return status;
    }

    @Override
    public StartDate getAddress() {
        return startDate;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }
/**
 * Assume addCommand is one of the valid add commands as defined in FlexibleCommand class
 * @param command
 * @return
 */
    public String getAddCommand(String addCommand) {
        StringBuilder sb = new StringBuilder();
        sb.append(addCommand + this.getName().fullName + " ");
        sb.append("b/" + this.getAddress().value + " ");
        sb.append("d/" + this.getPhone().value + " ");
        sb.append("s/" + this.getEmail().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
