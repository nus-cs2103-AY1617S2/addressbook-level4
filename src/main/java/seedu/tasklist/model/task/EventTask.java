package seedu.tasklist.model.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import seedu.tasklist.commons.util.CollectionUtil;
import seedu.tasklist.model.tag.UniqueTagList;
//@@author A0141993X
/**
* Represents a Task with start and end date in the task list.
* Guarantees: details are present and not null, field values are validated.
*/
public class EventTask extends Task implements ReadOnlyEventTask {

    private Name name;
    private Comment comment;
    private Priority priority;
    private Status status;
    private Date startDate;
    private Date endDate;

    private UniqueTagList tags;

    public static final String TYPE = "event";


    public EventTask(Name name, Comment comment, Priority priority, Status status,
                     Date startDate, Date endDate, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, startDate, endDate, status);
        this.name = name;
        this.comment = comment;
        this.priority = priority;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list

    }

    /**
     * Creates a copy of the given ReadOnlyEventTask.
     */
    public EventTask(ReadOnlyEventTask source) {
        this(source.getName(), source.getComment(), source.getPriority(),
             source.getStatus(), source.getStartDate(), source.getEndDate(), source.getTags());
    }

    public void setStartDate(Date startDate) {
        assert startDate != null;
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        assert endDate != null;
        this.endDate = endDate;
    }

    public void setPriority(Priority priority) {
        assert priority != null;
        this.priority = priority;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    public void setStatus(Status status) {
        assert status != null;
        this.status = status;
    }

    @Override
    public Status getStatus() {
        return status;
    }

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
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public String getStartDateString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(startDate);
    }

    //@@author A0143355J
    @Override
    public String getStartDateStringForUpcomingTask() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm");
        return dateFormat.format(startDate);
    }

    //@@author A0141993X
    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String getEndDateString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(endDate);
    }

    //@@author A0143355J
    @Override
    public String getEndDateStringForUpcomingTask() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm");
        return dateFormat.format(endDate);
    }

    //@@author A0141993X
    @Override
    public UniqueTagList getTags() {
        return tags;
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
    public void resetData(ReadOnlyEventTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setComment(replacement.getComment());
        this.setPriority(replacement.getPriority());
        this.setStatus(replacement.getStatus());
        this.setStartDate(replacement.getStartDate());
        this.setEndDate(replacement.getEndDate());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyEventTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyEventTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, startDate, endDate, tags);
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
}
