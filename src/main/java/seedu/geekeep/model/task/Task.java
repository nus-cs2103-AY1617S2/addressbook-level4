//@@author A0121658E
package seedu.geekeep.model.task;

import java.util.Objects;

import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.model.tag.UniqueTagList;

/**
 * Represents a Task in the Task Manager. Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask  {

    public static final String MESSAGE_DATETIME_MATCH_CONSTRAINTS =
            "Starting date and time must be matched with a ending date and time";
    public static final String MESSAGE_ENDDATETIME_LATER_CONSTRAINTS =
            "Starting date and time must be earlier than ending date and time";
    public static final int EVENT_PRIORITY = 0;
    public static final int FLOATING_TASK_PRIORITY = 1;
    public static final int DEADLINE_PRIORITY = 2;

    private Title title;
    private DateTime endDateTime;
    private DateTime startDateTime;
    private Description description;
    private boolean isDone;

    private UniqueTagList tags;

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) throws IllegalValueException {
        this(source.getTitle(), source.getStartDateTime(),
                source.getEndDateTime(), source.getLocation(), source.getTags(), source.isDone());
    }

    public Task(Title title, DateTime startDateTime,
                DateTime endDateTime, Description description,
                UniqueTagList tags, boolean isDone) throws IllegalValueException {
        assert title != null;
        if (startDateTime != null && endDateTime == null) {
            throw new IllegalValueException(MESSAGE_DATETIME_MATCH_CONSTRAINTS);
        }
        if (startDateTime != null && endDateTime != null
                && startDateTime.dateTime.isAfter(endDateTime.dateTime)) {
            throw new IllegalValueException(MESSAGE_ENDDATETIME_LATER_CONSTRAINTS);
        }

        this.title = title;
        this.endDateTime = endDateTime;
        this.startDateTime = startDateTime;
        this.description = description;
        this.isDone = isDone;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public DateTime getEndDateTime() {
        return endDateTime;
    }

    @Override
    public Description getLocation() {
        return description;
    }

    @Override
    public DateTime getStartDateTime() {
        return startDateTime;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, endDateTime, startDateTime, description, tags);
    }

    //@@author A0148037E
    /**
     * Get the task's priority which determines the ordering of index
     * @return int value of Priority
     */
    public int getPriority() {
        if (isEvent()) {
            return EVENT_PRIORITY;
        } else if (isFloatingTask()) {
            return FLOATING_TASK_PRIORITY;
        } else {
            assert isDeadline();
            return DEADLINE_PRIORITY;
        }
    }

    //@@author A0139438W
    /**
     * Get the task's DateTime that is used to compare date time.
     * For events, the startDateTime is used for comparison.
     * For deadlines, the endDateTime is used for comparison.
     * @return DateTime object
     */
    public DateTime getReferenceDateTime() {
        if (isEvent()) {
            return this.startDateTime;
        } else if (isDeadline()) {
            return this.endDateTime;
        } else {
            assert isFloatingTask();
            return null;
        }
    }

    /**
     * Compares this task's type priority with another.
     * @param otherTask
     * @return a comparator value, negative if less, positive if greater
     */
    public int comparePriority(Task otherTask) {
        return this.getPriority() - otherTask.getPriority();
    }

    /**
     * Compares this task's reference datetime with another in chronological order.
     * @param otherTask
     * @return a comparator value, negative if less, positive if greater
     */
    public int compareDate(Task otherTask) {
        assert !isFloatingTask() && !otherTask.isFloatingTask();
        return this.getReferenceDateTime().dateTime.compareTo(otherTask.getReferenceDateTime().dateTime);
    }

    /**
     * Compares this task's type priority and reference datetime with another.
     * Compares this task's title with another in lexicographic order if both are floating tasks.
     * @param otherTask
     * @return a comparator value, negative if less, positive if greater
     */
    public int comparePriorityAndDatetimeAndTitle(Task otherTask) {
        int comparePriorityResult = this.comparePriority(otherTask);
        if (comparePriorityResult != 0) {
            return comparePriorityResult;
        } else if (this.isFloatingTask() || otherTask.isFloatingTask()) {
            return this.compareTitle(otherTask);
        } else {
            return this.compareDate(otherTask);
        }
    }

    /**
     * Compares this task's title with another in lexicographic order.
     * @param otherTask
     * @return a comparator value, negative if less, positive if greater
     */
    public int compareTitle(Task otherTask) {
        return this.getTitle().toString().compareTo(otherTask.getTitle().toString());
    }

    //@@author A0121658E
    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setTitle(replacement.getTitle());
        this.setEndDateTime(replacement.getEndDateTime());
        this.setStartDateTime(replacement.getStartDateTime());
        this.setLocation(replacement.getLocation());
        this.setTags(replacement.getTags());
        this.setDone(replacement.isDone());
    }

    public void setStartDateTime(DateTime startDateTime) {
        assert startDateTime != null;
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(DateTime endDateTime) {
        assert endDateTime != null;
        this.endDateTime = endDateTime;
    }

    public void setLocation(Description description) {
        assert description != null;
        this.description = description;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Replaces this Task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    public void setTitle(Title title) {
        assert title != null;
        this.title = title;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public boolean isFloatingTask() {
        return startDateTime == null && endDateTime == null;
    }

    @Override
    public boolean isEvent() {
        return startDateTime != null && endDateTime != null;
    }

    @Override
    public boolean isDeadline() {
        return startDateTime == null && endDateTime != null;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    public void markDone() {
        setDone(true);
    }

    public void markUndone () {
        setDone(false);
    }

}
