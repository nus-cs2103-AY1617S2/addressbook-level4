package seedu.task.model.task;

import java.util.Objects;

import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.tag.UniqueTagList;

/**
 * Represents a Task in the tasks book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private TaskId id;
    private Description description;
    private DueDate dueDate;
    private Duration duration;
    private UniqueTagList tags;
    private Complete complete;


    /**
     * Constructor for Task
     * @param description must not be null
     * @param dueDate may be null
     * @param duration may be null
     * @param tags must not be null
     */
    public Task(Description description, DueDate dueDate, Duration duration, UniqueTagList tags, Complete complete) {
        assert !CollectionUtil.isAnyNull(description, tags, complete);
        this.description = description;
        this.dueDate = dueDate;
        this.duration = duration;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.complete = complete;
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getDueDate(), source.getDuration(), source.getTags(),
             source.getComplete());
    }

    @Override
    public TaskId getId() {
        return this.id;
    }

    /**
     * @param description must not be null
     */
    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    public void setDueDate(DueDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public DueDate getDueDate() {
        return dueDate;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    public Complete getComplete() {
        return complete;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setComplete() {
        this.complete.setComplete();
    }

    public void setNotComplete() {
        this.complete.setNotComplete();
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this tasks's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setDescription(replacement.getDescription());
        this.setDuration(replacement.getDuration());
        this.setDueDate(replacement.getDueDate());
        if (replacement.getComplete().getCompletion()) {
            this.setComplete();
        } else {
            this.setNotComplete();
        }
        this.setTags(replacement.getTags());
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
        return Objects.hash(description, duration, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public String getDurationStart() {
        return duration == null ? null : duration.getStartString();
    }

    @Override
    public String getDurationEnd() {
        return duration == null ? null : duration.getEndString();
    }
}
