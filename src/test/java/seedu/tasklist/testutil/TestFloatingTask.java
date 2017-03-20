//@@author A0139221N
package seedu.tasklist.testutil;

import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.Comment;
import seedu.tasklist.model.task.Name;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.ReadOnlyFloatingTask;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.Status;

public class TestFloatingTask extends TestTask implements ReadOnlyFloatingTask {

    private Name name;
    private Comment comment;
    private Priority priority;
    private Status status;
    private UniqueTagList tags;
    public static final String TYPE = "floating";

    /**
     * Creates a empty floating task with no details
     */
    public TestFloatingTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestFloatingTask(TestFloatingTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.comment = taskToCopy.getComment();
        this.priority = taskToCopy.getPriority();
        this.status = taskToCopy.getStatus();
        this.tags = taskToCopy.getTags();
    }

    /**
     * Sets the name of the task.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Sets the comment of the task.
     */
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    /**
     * Sets the tags of the task to {@code replacement}.
     * @param replacement
     */
    public void setTags(UniqueTagList replacement) {
        this.tags = replacement;
    }

    /**
     * Returns the name of the task.
     */
    @Override
    public Name getName() {
        return name;
    }

    /**
     * Returns the comment of the task.
     */
    @Override
    public Comment getComment() {
        return comment;
    }

    /**
     * Returns the tags of the task.
     */
    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    /**
     * Returns the priority of the task.
     */
    @Override
    public Priority getPriority() {
        return priority;
    }

    /**
     * Returns the status of the task.
     */
    @Override
    public Status getStatus() {
        return status;
    }

    /**
     * Returns a String version of the task.
     */
    @Override
    public String toString() {
        return getAsText();
    }

    /**
     * Generates a command line input to add this task.
     */
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append("c/" + this.getComment().value + " ");
        sb.append("p/" + this.getPriority().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns a String version of the task.
     * Format: name, comment, priority, tags.
     */
    @Override
    public String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Comment: ")
                .append(getComment())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Checks if the task is the same as another task
     */
    @Override
    public boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getComment().equals(this.getComment())
                && other.getPriority().equals(this.getPriority())
                && other.getStatus().equals(this.getStatus())
                && other.getTags().equals(this.getTags())
                && other.getType().equals(this.getType()));
    }

    /**
     * Returns the type of the task.
     */
    @Override
    public String getType() {
        return TYPE;
    }

    /**
     * Sets the priority of the task
     */
    @Override
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Sets the status of the task.
     */
    @Override
    public void setStatus(Status status) {
        this.status = status;
    }
}
