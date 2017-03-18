//@@author A0139221N
package seedu.tasklist.testutil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.Comment;
import seedu.tasklist.model.task.Name;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.ReadOnlyEventTask;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.Status;

public class TestEventTask extends TestTask implements ReadOnlyEventTask {

    private Name name;
    private Comment comment;
    private Priority priority;
    private Status status;
    private UniqueTagList tags;
    private Date startDate;
    private Date endDate;
    public static final String TYPE = "event";

    /**
     * Creates a empty event task with no details
     */
    public TestEventTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestEventTask(TestEventTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.comment = taskToCopy.getComment();
        this.priority = taskToCopy.getPriority();
        this.status = taskToCopy.getStatus();
        this.startDate = taskToCopy.getStartDate();
        this.endDate = taskToCopy.getEndDate();
        this.tags = taskToCopy.getTags();
    }

    /**
     * Returns the type of the task.
     */
    @Override
    public String getType() {
        return TYPE;
    }

    /**
     * Returns the start date of the event task.
     */
    @Override
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * Returns the end date of the event task.
     */
    @Override
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * Sets the name of the task.
     */
    @Override
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Returns the name of the task.
     */
    @Override
    public Name getName() {
        return this.name;
    }

    /**
     * Sets the comment of the task.
     */
    @Override
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    /**
     * Returns the comment of the task.
     */
    @Override
    public Comment getComment() {
        return this.comment;
    }

    /**
     * Returns the tags of the task.
     */
    @Override
    public UniqueTagList getTags() {
        return this.tags;
    }

    /**
     * Sets the priority of the task
     */
    @Override
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Returns the priority of the task.
     */
    @Override
    public Priority getPriority() {
        return this.priority;
    }

    /**
     * Sets the status of the task.
     */
    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Returns the status of the task.
     */
    @Override
    public Status getStatus() {
        return this.status;
    }

    /**
     * Generates a command line input to add this task.
     */
    @Override
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append("d/" + this.getStartDate().getMonth() + "-" + this.getStartDate().getDay() + "-"
                + this.getStartDate().getYear() + " " + this.getStartDate().getHours() + ":"
                + this.getStartDate().getMinutes() + ":" + this.getStartDate().getSeconds() + " ");
        sb.append("to " + this.getEndDate().toString() + " ");
        sb.append("c/" + this.getComment().value + " ");
        sb.append("p/" + this.getPriority().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns a String version of the task.
     */
    @Override
    public String toString() {
        return getAsText();
    }

    /**
     * Returns a String version of the task.
     * Format: name, start date, end date, comment, priority, tags.
     */
    @Override
    public String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" StartDate: ")
                .append(getStartDateString())
                .append(" EndDate: ")
                .append(getEndDateString())
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
                && other.getType().equals(this.getType())
                && ((ReadOnlyEventTask) other).getStartDate().equals(this.getStartDate())
                && ((ReadOnlyEventTask) other).getEndDate().equals(this.getEndDate()));
    }

    /**
     * Sets the tags of the task to {@code replacement}.
     * @param replacement
     */
    @Override
    public void setTags(UniqueTagList replacement) {
        this.tags = replacement;
    }

    /**
     * Sets the start date of the event task to {@code startDate}.
     * @param startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Sets the end date of the event task to {@code endDate}.
     * @param endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Returns the start date as a String in the format dd/MM/yyyy HH:mm:ss
     */
    @Override
    public String getStartDateString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(this.startDate);
    }

    /**
     * Returns the end date as a String in the format dd/MM/yyyy HH:mm:ss
     */
    @Override
    public String getEndDateString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(this.endDate);
    }
}
