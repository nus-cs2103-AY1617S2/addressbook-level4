package seedu.bulletjournal.testutil;

import seedu.bulletjournal.model.tag.UniqueTagList;
import seedu.bulletjournal.model.task.BeginDate;
import seedu.bulletjournal.model.task.DueDate;
import seedu.bulletjournal.model.task.ReadOnlyTask;
import seedu.bulletjournal.model.task.Status;
import seedu.bulletjournal.model.task.TaskName;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private TaskName taskName;
    private BeginDate beginDate;
    private Status status;
    private DueDate dueDate;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.taskName = taskToCopy.getTaskName();
        this.dueDate = taskToCopy.getPhone();
        this.status = taskToCopy.getStatus();
        this.beginDate = taskToCopy.getAddress();
        this.tags = taskToCopy.getTags();
    }

    public void setName(TaskName taskName) {
        this.taskName = taskName;
    }

    public void setAddress(BeginDate beginDate) {
        this.beginDate = beginDate;
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
    public TaskName getTaskName() {
        return taskName;
    }

    @Override
    public DueDate getPhone() {
        return dueDate;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public BeginDate getAddress() {
        return beginDate;
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
        sb.append(addCommand + this.getTaskName().fullName + " ");
        sb.append("b/" + this.getAddress().value + " ");
        sb.append("d/" + this.getPhone().value + " ");
        sb.append("s/" + this.getStatus().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
    
    public String getAddCommandFloating(String addCommand) {
        StringBuilder sb = new StringBuilder();
        sb.append(addCommand + this.getTaskName().fullName + " ");
        sb.append("s/" + this.getStatus().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
