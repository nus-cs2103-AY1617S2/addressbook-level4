package seedu.task.testutil;

import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Complete;
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
    private DueDate dueDate;
    private Duration duration;
    private UniqueTagList tags;
    private Complete complete;

    public TestTask() {
        tags = new UniqueTagList();
        complete = new Complete(false);
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.id = taskToCopy.getTaskId();
        this.description = taskToCopy.getDescription();
        this.dueDate = taskToCopy.getDueDate();
        this.duration = taskToCopy.getDuration();
        this.tags = taskToCopy.getTags();
        this.complete = taskToCopy.getComplete();
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    //@@author A0163744B
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setTaskId(TaskId id) {
        this.id = id;
    }
    //@@author

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    //@@author A160004845
    public void setDueDate(DueDate dueDate) {
        this.dueDate = dueDate;
    }
    //@@author

    //@@author A0163744B
    public void setCompletion(Complete completion) {
        this.complete = completion;
    }

    @Override
    public TaskId getTaskId() {
        return this.id;
    }
    //@@author

    @Override
    public Description getDescription() {
        return description;
    }

    //@@author A160004845
    @Override
    public DueDate getDueDate() {
        return this.dueDate;
    }
    //@@author

    //@@author A0163744B
    @Override
    public Duration getDuration() {
        return duration;
    }
    //@@author

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

    //@@author A0163673Y
    @Override
    public String getDurationStart() {
        return duration == null ? null : duration.getStartString();
    }

    @Override
    public String getDurationEnd() {
        return duration == null ? null : duration.getEndString();
    }
    //@@author

    @Override
    public Complete getComplete() {
        return this.complete;
    }

}
