package seedu.watodo.testutil;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.commons.util.CollectionUtil;
import seedu.watodo.model.tag.UniqueTagList;
import seedu.watodo.model.task.DateTime;
import seedu.watodo.model.task.Description;
import seedu.watodo.model.task.ReadOnlyTask;
import seedu.watodo.model.task.TaskStatus;
import seedu.watodo.model.task.TaskType;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Description description;
    private DateTime startDate;
    private DateTime endDate;
    private TaskStatus status; //Default status of any new task created is UNDONE
    private UniqueTagList tags;
    private TaskType taskType;

    public TestTask() throws IllegalValueException {
        description = new Description("");
        startDate = null;
        endDate = null;
        status = TaskStatus.UNDONE;
        tags = new UniqueTagList();
        taskType = TaskType.FLOAT;

    };

    /* Constructs a Floating TestTask object from a given description. */
    public TestTask(Description description, UniqueTagList tags) {
        this(description, null, null, tags);
    }

    /* Constructs a Deadline TestTask object from a given description. */
    public TestTask(Description description, DateTime deadline, UniqueTagList tags) {
        this(description, null, deadline, tags);
    }

    /* Constructs an Event TestTask object from a given description. */
    public TestTask(Description description, DateTime startDate, DateTime endDate, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, tags);
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.status = TaskStatus.UNDONE;
    }
    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this(taskToCopy.getDescription(), taskToCopy.getTags());
        if (taskToCopy.getStartDate() != null) {
            this.setStartDate(taskToCopy.getStartDate());
        }
        if (taskToCopy.getEndDate() != null) {
            this.setEndDate(taskToCopy.getEndDate());
        }
    }

    public void setDescription(Description description) {
        assert description != null;
        this.description = description;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    /* Changes the current status of the task. */
    public void setStatus(TaskStatus newStatus) {
        this.status = newStatus;
    }

    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public DateTime getStartDate() {
        return startDate;
    }

    @Override
    public DateTime getEndDate() {
        return endDate;
    }

    @Override
    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(); //TO DO the timing
        builder.append(getAsText());
        if (startDate != null) {
            builder.append("\nStart: ").append(startDate);
        }
        if (endDate != null) {
            builder.append("\nEnd: ").append(endDate);
        }
        return builder.toString();
    }

    public String getAddCommand() {
        //TO DO update
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getDescription().fullDescription + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public TaskType getTaskType() {
        return taskType;
    }

}
