//@@author A0139221N
package seedu.tasklist.testutil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import seedu.tasklist.commons.util.IntegerUtil;
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

    /**
     * Creates a empty deadline task with no details
     */
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

    /**
     * Returns the type of the task.
     */
    @Override
    public String getType() {
        return TYPE;
    }

    /**
     * Returns the deadline of the deadline task.
     */
    @Override
    public Date getDeadline() {
        return this.deadline;
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
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(this.deadline);
        sb.append("add " + this.getName().fullName + " ");
        String minutes = (IntegerUtil.isSingleDigit(currentDate.get(Calendar.MINUTE)))
                            ? "0" + Integer.toString(currentDate.get(Calendar.MINUTE))
                            : Integer.toString(currentDate.get(Calendar.MINUTE));
        String seconds = (IntegerUtil.isSingleDigit(currentDate.get(Calendar.SECOND)))
                            ? "0" + Integer.toString(currentDate.get(Calendar.SECOND))
                            : Integer.toString(currentDate.get(Calendar.SECOND));
        sb.append("d/" + (currentDate.get(Calendar.MONTH) + 1) + "-"
                + currentDate.get(Calendar.DATE) + "-"
                + currentDate.get(Calendar.YEAR) + " "
                + currentDate.get(Calendar.HOUR) + ":"
                + minutes + ":"
                + seconds + " ");
        sb.append("c/" + this.getComment().value + " ");
        sb.append("p/" + this.getPriority().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns a String version of the task.
     * Format: name, deadline, comment, priority, tags.
     */
    @Override
    public String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Deadline: ")
                .append(getDeadlineString())
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
                && ((ReadOnlyDeadlineTask) other).getDeadline().equals(this.getDeadline()));
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
     * Returns a String version of the task.
     */
    @Override
    public String toString() {
        return getAsText();
    }

    /**
     * Sets the deadline of the deadline task to {@code deadline}.
     * @param deadline
     */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /**
     * Returns the deadline as a String in the format dd/MM/yyyy HH:mm:ss
     */
    @Override
    public String getDeadlineString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(this.deadline);
    }

    //@@author A0143355J
    @Override
    public String getDeadlineStringForUpcomingTask() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm");
        return dateFormat.format(deadline);
    }
}
