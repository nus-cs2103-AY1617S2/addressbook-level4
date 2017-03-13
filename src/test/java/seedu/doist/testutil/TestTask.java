package seedu.doist.testutil;

import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.Description;
import seedu.doist.model.task.FinishedStatus;
import seedu.doist.model.task.Priority;
import seedu.doist.model.task.ReadOnlyTask;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Description desc;
    private Priority priority;
    private FinishedStatus finishedStatus;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
        finishedStatus = new FinishedStatus();
        priority = new Priority();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.desc = taskToCopy.getDescription();
        this.priority = taskToCopy.getPriority();
        this.finishedStatus = taskToCopy.getFinishedStatus();
        this.tags = taskToCopy.getTags();
    }

    public void setName(Description desc) {
        this.desc = desc;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setFinishedStatus(boolean isFinished) {
        this.finishedStatus.setIsFinished(isFinished);
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Description getDescription() {
        return desc;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public FinishedStatus getFinishedStatus() {
        return finishedStatus;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getDescription().desc + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
