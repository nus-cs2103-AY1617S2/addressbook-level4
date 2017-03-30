package seedu.bulletjournal.model.task;

import java.util.Objects;

import seedu.bulletjournal.commons.util.CollectionUtil;
import seedu.bulletjournal.model.tag.UniqueTagList;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private TaskName taskName;
    private DueDate dueDate;
    private Status status;
    private BeginDate beginDate;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(TaskName taskName, DueDate dueDate, Status status, BeginDate beginDate, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(taskName, dueDate, status, beginDate, tags);
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.status = status;
        this.beginDate = beginDate;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTaskName(), source.getPhone(), source.getStatus(), source.getAddress(), source.getTags());
    }

    public void setName(TaskName taskName) {
        assert taskName != null;
        this.taskName = taskName;
    }

    @Override
    public TaskName getTaskName() {
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
    public Status getStatus() {
        return status;
    }

    public void setAddress(BeginDate beginDate) {
        assert beginDate != null;
        this.beginDate = beginDate;
    }

    @Override
    public BeginDate getAddress() {
        return beginDate;
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

        this.setName(replacement.getTaskName());
        this.setPhone(replacement.getPhone());
        this.setEmail(replacement.getStatus());
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
        return Objects.hash(taskName, dueDate, status, beginDate, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
