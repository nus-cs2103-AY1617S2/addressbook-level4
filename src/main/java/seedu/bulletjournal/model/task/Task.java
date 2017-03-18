package seedu.bulletjournal.model.task;

import java.util.Objects;

import seedu.bulletjournal.commons.util.CollectionUtil;
import seedu.bulletjournal.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private TaskName taskName;
    private DueDate dueDate;
    private Status status;
    private StartDate startDate;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(TaskName taskName, DueDate dueDate, Status status, StartDate startDate, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(taskName, dueDate, status, startDate, tags);
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.status = status;
        this.startDate = startDate;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getTags());
    }

    public void setName(TaskName taskName) {
        assert taskName != null;
        this.taskName = taskName;
    }

    @Override
    public TaskName getName() {
        return taskName;
    }

    public void setPhone(DueDate dueDate) {
        assert dueDate != null;
        this.dueDate = dueDate;
    }

    @Override
    public DueDate getPhone() {
        return dueDate;
    }

    public void setEmail(Status status) {
        assert status != null;
        this.status = status;
    }

    @Override
    public Status getEmail() {
        return status;
    }

    public void setAddress(StartDate startDate) {
        assert startDate != null;
        this.startDate = startDate;
    }

    @Override
    public StartDate getAddress() {
        return startDate;
    }

    @Override
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
     * Updates this person with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setPhone(replacement.getPhone());
        this.setEmail(replacement.getEmail());
        this.setAddress(replacement.getAddress());
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
        return Objects.hash(taskName, dueDate, status, startDate, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
