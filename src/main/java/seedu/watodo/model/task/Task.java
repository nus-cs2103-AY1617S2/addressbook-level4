package seedu.watodo.model.task;

import java.util.Objects;

import seedu.watodo.commons.util.CollectionUtil;
import seedu.watodo.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager. A task can have no start and end dates (floating tasks),
 * only end date (deadline), or both start and end dates (events).
 * Guarantees: all other details except startDate and EndDate are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Description description;
    private DateTime startDate;
    private DateTime endDate;
    private UniqueTagList tags;
    private TaskStatus status = TaskStatus.UNDONE; //Default status of any new task created is UNDONE
    private TaskType taskType;

    /* Constructs a Floating Task object from a given description. */
    public Task(Description description, UniqueTagList tags) {
        this(description, null, null, tags);
        this.taskType = TaskType.FLOAT;
    }

    /* Constructs a Floating Task object from a given description. With Status */
    public Task(Description description, UniqueTagList tags, TaskStatus newStatus) {
        this(description, null, null, tags, newStatus);
        this.taskType = TaskType.FLOAT;
    }

    /* Constructs a Deadline Task object from a given description. */
    public Task(Description description, DateTime deadline, UniqueTagList tags) {
        this(description, null, deadline, tags);
        this.taskType = TaskType.DEADLINE;
    }

    /* Constructs a Deadline Task object from a given description. With status. */
    public Task(Description description, DateTime deadline, UniqueTagList tags, TaskStatus newStatus) {
        this(description, null, deadline, tags, newStatus);
        this.taskType = TaskType.DEADLINE;
    }

    /* Constructs an Event Task object from a given description. */
    public Task(Description description, DateTime startDate, DateTime endDate, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, tags);
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.taskType = TaskType.EVENT;
    }

    /* Constructs an Event Task object from a given description. With status */
    public Task(Description description, DateTime startDate, DateTime endDate, UniqueTagList tags, TaskStatus status) {
        assert !CollectionUtil.isAnyNull(description, tags);
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.status = status;
        this.taskType = TaskType.EVENT;
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getStartDate(), source.getEndDate(), source.getTags(), source.getStatus());
    }

    @Override
    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    @Override
    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    @Override
    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public TaskStatus getStatus() {
        return status;
    }

    /* Changes the current status of the task. */
    public void setStatus(TaskStatus newStatus) {
        this.status = newStatus;
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
        this.setStartDate(replacement.getStartDate());
        this.setEndDate(replacement.getEndDate());
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
        return Objects.hash(description, startDate, endDate, status, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getAsText());
        return builder.toString();
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

}
