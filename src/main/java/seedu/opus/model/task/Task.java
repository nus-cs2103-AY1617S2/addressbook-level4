package seedu.opus.model.task;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.commons.util.CollectionUtil;
import seedu.opus.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    //@@author A0126345J
    private Name name;
    private Priority priority;
    private Status status;
    private Note note;
    private DateTime startTime;
    private DateTime endTime;

    private UniqueTagList tags;
    //@@author

    /**
     * Accepts null values for priority, note and deadline only.
     * @param name
     * @param priority
     * @param status
     * @param note
     * @param deadline
     * @param tags
     */
    public Task(Name name, Priority priority, Status status,
            Note note, DateTime startTime, DateTime endTime, UniqueTagList tags) {
        //@@author A0124368A
        // Name should never be null because it is required for each task.
        // Status should never be null because every created task should be marked as incomplete.
        // Tags should never be null because zero tags is represented as an empty list.
        assert !CollectionUtil.isAnyNull(name, status, tags);
        //@@author

        this.name = name;
        this.priority = priority;
        this.status = status;
        this.note = note;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        //@@author A0124368A
        this(source.getName(), source.getPriority().orElse(null), source.getStatus(),
                source.getNote().orElse(null), source.getStartTime().orElse(null),
                source.getEndTime().orElse(null), source.getTags());
        //@@author
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setPriority(Priority priority) {
        assert priority != null;
        this.priority = priority;
    }

    //@@author A0124368A
    @Override
    public Optional<Priority> getPriority() {
        return Optional.ofNullable(priority);
    }
    //@@author

    public void setStatus(Status status) {
        assert status != null;
        this.status = status;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public void setNote(Note note) {
        assert note != null;
        this.note = note;
    }

    //@@author A0124368A
    @Override
    public Optional<Note> getNote() {
        return Optional.ofNullable(note);
    }

    @Override
    public Optional<DateTime> getStartTime() {
        if (startTime != null && startTime.dateTime == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(startTime);
    }
    //@@author

    public void setStartTime(DateTime dateTime) {
        this.startTime = dateTime;
    }

    //@@author A0124368A
    @Override
    public Optional<DateTime> getEndTime() {
        if (endTime != null && endTime.dateTime == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(endTime);
    }
    //@@author

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
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
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setPriority(replacement.getPriority().orElse(null));
        this.setStatus(replacement.getStatus());
        this.setNote(replacement.getNote().orElse(null));
        this.setStartTime(replacement.getStartTime().orElse(null));
        this.setEndTime(replacement.getEndTime().orElse(null));
        this.setTags(replacement.getTags());
    }

    //@@author A0126345J
    /**
     * Returns true if the end time is later than both the start time and the current time
     */
    public static boolean isValidEvent(ReadOnlyTask toCheck) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime endTime;
        LocalDateTime startTime;

        // If both start time and end time does not exist
        if (!toCheck.getEndTime().isPresent() && !toCheck.getEndTime().isPresent()) {
            return true;
        }

        // If only start time exists
        if (!toCheck.getEndTime().isPresent() && toCheck.getStartTime().isPresent()) {
            return false;
        }

        try {
            endTime = toCheck.getEndTime().orElse(new DateTime(currentTime)).dateTime;
            startTime = toCheck.getStartTime().orElse(new DateTime(currentTime)).dateTime;
            return startTime.isBefore(endTime) && endTime.isAfter(currentTime);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        return false;
    }
    //@@author

    //@@author A0148087W
    /**
     * Check if the task is a Event with a StartDate and EndDate
     * @return
     */
    public boolean isEvent() {
        return this.getStartTime().isPresent() ? true : false;
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, priority, status, note, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
