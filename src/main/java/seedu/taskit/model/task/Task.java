// @@author A0163996J

package seedu.taskit.model.task;

import java.util.Objects;

import seedu.taskit.commons.exceptions.IllegalValueException;
import seedu.taskit.model.tag.UniqueTagList;
import seedu.taskit.model.task.Date;
import static seedu.taskit.commons.core.Messages.MESSAGE_INVALID_DATES;

public class Task implements ReadOnlyTask{

    protected Title title;
    protected Date start;
    protected Date end;
    protected Priority priority;

    protected UniqueTagList tags;

    private boolean isDone;
    private boolean isOverdue;

    /**
     * Constructor for tasks
     * @param title
     * @param start
     * @param end
     * @param priority
     * @param tags
     * @throws IllegalValueException
     */
    public Task(Title title, Date start, Date end, Priority priority, UniqueTagList tags) throws IllegalValueException {
        this.title = title;
        if (!start.isStartValidComparedToEnd(end)) {
            throw new IllegalValueException(MESSAGE_INVALID_DATES);
        }
        this.start = start;
        this.end = end;
        this.priority = priority;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.isDone = false;
        this.isOverdue = checkOverdue();
    }

    /**
     * Creates a copy of the given Task.
     */
    public Task(ReadOnlyTask source) {
        this.title = source.getTitle();
        this.start = source.getStart();
        this.end = source.getEnd();
        this.priority = source.getPriority();
        this.tags = new UniqueTagList(source.getTags());
        this.isDone = source.isDone();
        this.isOverdue = source.isOverdue();
    }

    public void setTitle(Title title) {
        assert title != null;
        this.title = title;
    }

    public Title getTitle() {
        return title;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStart() {
      if (start == null) {
        return new Date();
      }
        return start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getEnd() {
      if (end == null) {
        return new Date();
      }
        return end;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Priority getPriority() {
        return priority;
    }

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setTitle(replacement.getTitle());
        this.setStart(replacement.getStart());
        this.setEnd(replacement.getEnd());
        this.setPriority(replacement.getPriority());
        this.setTags(replacement.getTags());
    }

    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Task // instanceof handles nulls
                && this.isSameStateAs((Task) other));
    }

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    public boolean isSameStateAs(Task other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTitle().equals(this.getTitle())
                && other.getStart().equals(this.getStart())
                && other.getEnd().equals(this.getEnd())
                && other.getPriority().equals(this.getPriority())
                && other.getTags().equals(this.getTags()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, tags);
    }

    public String toString() {
        return getAsText();
    }

    /**
     * Formats the task as text, showing all details.
     */
    public String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle() + " ")
            .append("Start: ")
               .append(getStart() + " ")
               .append("End: ")
               .append(getEnd() + " ")
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();

    }

    //@@author A0141872E
    @Override
    public Boolean isDone() {
        return isDone;
    }

    public void setDone(String status) {
        if(status.equals("done")) {
            isDone = true;
        } else {
            isDone = false;
        }
    }

    public void setOverdue() {
        this.isOverdue = checkOverdue();
    }

    @Override
    public Boolean isOverdue() {
        return checkOverdue();
    }

    private boolean checkOverdue() {
        return this.end.isEndTimePassCurrentTime();
    }//@@author
}
