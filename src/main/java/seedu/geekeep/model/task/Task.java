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
    private Location location;
    private boolean isDone;

    private UniqueTagList tags;

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) throws IllegalValueException {
        this(source.getTitle(), source.getStartDateTime(),
                source.getEndDateTime(), source.getLocation(), source.getTags(), source.isDone());
    }

    /**
     * Every field must be present and not null.
     */
    public Task(Title title, DateTime startDateTime,
                DateTime endDateTime, Location location,
                UniqueTagList tags) throws IllegalValueException {
        this(title, startDateTime, endDateTime, location, tags, false);
    }

    public Task(Title title, DateTime startDateTime,
                DateTime endDateTime, Location location,
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
        this.location = location;
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
    public Location getLocation() {
        return location;
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
        return Objects.hash(title, endDateTime, startDateTime, location, tags);
    }

    /**
     * Computes task's priority which determines the ordering of index
     */
    public int computePriority() {
        if (isEvent()) {
            return EVENT_PRIORITY;
        } else if (isFloatingTask()) {
            return FLOATING_TASK_PRIORITY;
        } else {
            assert isDeadline();
            return DEADLINE_PRIORITY;
        }
    }

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

    public void setLocation(Location location) {
        assert location != null;
        this.location = location;
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
