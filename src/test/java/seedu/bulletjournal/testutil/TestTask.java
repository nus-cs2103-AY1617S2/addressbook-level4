package seedu.bulletjournal.testutil;

import seedu.bulletjournal.model.tag.UniqueTagList;
import seedu.bulletjournal.model.task.Deadline;
import seedu.bulletjournal.model.task.ReadOnlyTask;
import seedu.bulletjournal.model.task.StartTime;
import seedu.bulletjournal.model.task.Status;
import seedu.bulletjournal.model.task.TaskName;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private TaskName taskName;
    private StartTime startTime;
    private Status status;
    private Deadline deadline;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestTask(TestTask personToCopy) {
        this.taskName = personToCopy.getName();
        this.deadline = personToCopy.getPhone();
        this.status = personToCopy.getEmail();
        this.startTime = personToCopy.getAddress();
        this.tags = personToCopy.getTags();
    }

    public void setName(TaskName taskName) {
        this.taskName = taskName;
    }

    public void setAddress(StartTime startTime) {
        this.startTime = startTime;
    }

    public void setEmail(Status status) {
        this.status = status;
    }

    public void setPhone(Deadline deadline) {
        this.deadline = deadline;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public TaskName getName() {
        return taskName;
    }

    @Override
    public Deadline getPhone() {
        return deadline;
    }

    @Override
    public Status getEmail() {
        return status;
    }

    @Override
    public StartTime getAddress() {
        return startTime;
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
        sb.append("a/" + this.getAddress().value + " ");
        sb.append("p/" + this.getPhone().value + " ");
        sb.append("e/" + this.getEmail().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
