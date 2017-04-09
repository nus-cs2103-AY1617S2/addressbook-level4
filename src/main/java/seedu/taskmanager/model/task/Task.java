package seedu.taskmanager.model.task;

import java.util.Objects;
import java.util.Optional;

import seedu.taskmanager.commons.util.CollectionUtil;
import seedu.taskmanager.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Title title;
    // @@author A0140032E
    private Optional<StartDate> startDate;
    private Optional<EndDate> endDate;
    private Optional<Description> description;
    private Optional<Repeat> repeat;
    // @@author

    // @@author A0114269E
    private Status status;
    // @@author

    private UniqueTagList tags;

    // @@author A0140032E
    /**
     * Every field must be present and not null.
     */
    public Task(Title title, Optional<StartDate> startDate, Optional<EndDate> endDate,
            Optional<Description> description, Optional<Repeat> repeat, Status status, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(title, endDate, description, tags);
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.repeat = repeat;
        this.tags = new UniqueTagList(tags); // protect internal tags from
                                             // changes in the arg list
        this.status = status;
    }
    // @@author

    // @@author A0114269E
    /**
     * Constructor for Task without Status parameter.
     */
    public Task(Title title, Optional<StartDate> startDate, Optional<EndDate> endDate,
            Optional<Description> description, Optional<Repeat> repeat, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(title, endDate, description, tags);
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.repeat = repeat;
        this.tags = new UniqueTagList(tags); // protect internal tags from
                                             // changes in the arg list
        this.status = new Status();
    }
    // @@author

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTitle(), source.getStartDate(), source.getEndDate(), source.getDescription(), source.getRepeat(),
                source.getStatus(), source.getTags());
    }

    public void setTitle(Title title) {
        assert title != null;
        this.title = title;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    // @@author A0140032E
    public void setStartDate(Optional<StartDate> startDate) {
        this.startDate = startDate;
    }

    @Override
    public Optional<StartDate> getStartDate() {
        return startDate;
    }

    public void setEndDate(Optional<EndDate> endDate) {
        assert endDate != null;
        this.endDate = endDate;
    }

    @Override
    public Optional<EndDate> getEndDate() {
        return endDate;
    }

    public void setDescription(Optional<Description> description) {
        assert description != null;
        this.description = description;
    }

    @Override
    public Optional<Description> getDescription() {
        return description;
    }

    @Override
    public Optional<Repeat> getRepeat() {
        return repeat;
    }

    public void setRepeat(Optional<Repeat> repeat) {
        assert repeat != null;
        this.repeat = repeat;
    }
    // @@author

    // @@author A0114269E
    @Override
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        assert status != null;
        this.status = status;
    }
    // @@author

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

        this.setTitle(replacement.getTitle());
        this.setStartDate(replacement.getStartDate());
        this.setEndDate(replacement.getEndDate());
        this.setDescription(replacement.getDescription());
        this.setRepeat(replacement.getRepeat());
        this.setStatus(replacement.getStatus());
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
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(title, startDate, endDate, description, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
