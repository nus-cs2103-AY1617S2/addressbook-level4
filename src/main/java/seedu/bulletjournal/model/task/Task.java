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
    private Deadline deadline;
    private Status status;
    private BeginTime beginTime;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(TaskName taskName, Deadline deadline, Status status, BeginTime beginTime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(taskName, deadline, status, beginTime, tags);
        this.taskName = taskName;
        this.deadline = deadline;
        this.status = status;
        this.beginTime = beginTime;
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

    public void setPhone(Deadline deadline) {
        assert deadline != null;
        this.deadline = deadline;
    }

    @Override
    public Deadline getPhone() {
        return deadline;
    }

    public void setEmail(Status status) {
        assert status != null;
        this.status = status;
    }

    @Override
    public Status getEmail() {
        return status;
    }

    public void setAddress(BeginTime beginTime) {
        assert beginTime != null;
        this.beginTime = beginTime;
    }

    @Override
    public BeginTime getAddress() {
        return beginTime;
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
        return Objects.hash(taskName, deadline, status, beginTime, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
