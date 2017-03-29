package seedu.tasklist.model.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import seedu.tasklist.commons.util.CollectionUtil;
import seedu.tasklist.model.tag.UniqueTagList;
//@@author A0141993X
/**
* Represents a Task with duedate in the task list.
* Guarantees: details are present and not null, field values are validated.
*/
public class DeadlineTask extends Task implements ReadOnlyDeadlineTask {

    private Name name;
    private Comment comment;
    private Priority priority;
    private Status status;
    private Date deadline;

    private UniqueTagList tags;

    public static final String TYPE = "deadline";

    public DeadlineTask(Name name, Comment comment, Priority priority,
                        Status status, Date deadline, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, deadline, status);
        this.name = name;
        this.comment = comment;
        this.priority = priority;
        this.status = status;
        this.deadline = deadline;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list

    }

    /**
     * Creates a copy of the given ReadOnlyDeadlineTask.
     */
    public DeadlineTask(ReadOnlyDeadlineTask source) {
        this(source.getName(), source.getComment(), source.getPriority(),
             source.getStatus(), source.getDeadline(), source.getTags());
    }

    public void setDeadline(Date deadline) {
        assert deadline != null;
        this.deadline = deadline;
    }

    @Override
    public Date getDeadline() {
        return deadline;
    }

    @Override
    public String getDeadlineString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(deadline);
    }

    //@@author A0143355J
    @Override
    public String getDeadlineStringForUpcomingTask() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm");
        return dateFormat.format(deadline);
    }

    //@@author A0141993X
    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setComment(Comment comment) {
        assert comment != null;
        this.comment = comment;
    }

    @Override
    public Comment getComment() {
        return comment;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    public void setPriority(Priority priority) {
        assert priority != null;
        this.priority = priority;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setStatus(Status status) {
        assert status != null;
        this.status = status;
    }


    public Status getStatus() {
        return status;
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyDeadlineTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setComment(replacement.getComment());
        this.setPriority(replacement.getPriority());
        this.setStatus(replacement.getStatus());
        this.setDeadline(replacement.getDeadline());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyDeadlineTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyDeadlineTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, deadline, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public String getType() {
        return TYPE;
    }

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
}
