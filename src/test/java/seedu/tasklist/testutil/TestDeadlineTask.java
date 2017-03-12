package seedu.tasklist.testutil;

import java.util.Date;

import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.Comment;
import seedu.tasklist.model.task.Name;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.ReadOnlyDeadlineTask;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.Status;

public class TestDeadlineTask extends TestTask implements ReadOnlyDeadlineTask {

    private Name name;
    private Comment comment;
    private Priority priority;
    private Status status;
    private UniqueTagList tags;
    private Date deadline;
    public static final String TYPE = "deadline";

    public TestDeadlineTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestDeadlineTask(TestDeadlineTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.comment = taskToCopy.getComment();
        this.priority = taskToCopy.getPriority();
        this.status = taskToCopy.getStatus();
        this.deadline = taskToCopy.getDeadline();
        this.tags = taskToCopy.getTags();
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public Date getDeadline() {
        return this.deadline;
    }

    @Override
    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public Name getName() {
        return this.name;
    }

    @Override
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public Comment getComment() {
        return this.comment;
    }

    @Override
    public UniqueTagList getTags() {
        return this.tags;
    }

    @Override
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public Priority getPriority() {
        return this.priority;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append("d/ " + this.getDeadline().toString() + " ");
        sb.append("c/" + this.getComment().value + " ");
        sb.append("p/" + this.getPriority().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Deadline: ")
                .append(getDeadline())
                .append(" Status: ")
                .append(getStatus())
                .append(" Priority ")
                .append(getPriority())
                .append(" Comment: ")
                .append(getComment())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    @Override
    public boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getComment().equals(this.getComment())
                && other.getPriority().equals(this.getPriority())
                && other.getStatus().equals(this.getStatus())
                && other.getTags().equals(this.getTags())
                && other.getType().equals(this.getType())
                && ((ReadOnlyDeadlineTask) other).getDeadline().equals(this.getDeadline()));
    }

    @Override
    public void setTags(UniqueTagList replacement) {
        this.tags = replacement;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
