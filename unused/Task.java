package seedu.watodo.model.task;

import java.util.Objects;

import seedu.watodo.commons.util.CollectionUtil;
import seedu.watodo.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager. Task can be of type floating, deadline or event.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    public enum Status { UNDONE, DONE, OVERDUE } // Represents a Task's current status in the task manager.

    protected Description description;
    protected Status status;
    protected UniqueTagList tags;

    /* Constructs a Task object from a given description. Default status is UNDONE. */
    public Task(Description description, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, tags);
        this.description = description;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        status = Status.UNDONE;
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getTags());
    }

    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    /* Changes the current status of the task. */
    public void setStatus(Status newStatus) {
        this.status = newStatus;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /* Replaces this task's tags with the tags in the argument tag list. */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setDescription(replacement.getDescription());
        this.setTags(replacement.getTags());
        this.setStatus(replacement.getStatus());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
