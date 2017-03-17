package seedu.task.testutil;

import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Description;
import seedu.task.model.task.DueDate;
import seedu.task.model.task.Duration;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.TaskId;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private TaskId id;
    private Description description;
    private Duration duration;
    private UniqueTagList tags;
    private DueDate dueDate;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.description = taskToCopy.getDescription();
        this.tags = taskToCopy.getTags();
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }


    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    public void setDueDate(DueDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public TaskId getId() {
        return this.id;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public DueDate getDueDate() {
        return this.dueDate;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getDescription().description + " ");

        Duration duration = this.getDuration();
        if (duration != null) {
            sb.append(" starts/" + duration.getStartString() + " ");
            sb.append(" ends/" + duration.getEndString() + " ");
        }

        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
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
