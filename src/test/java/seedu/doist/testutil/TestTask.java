package seedu.doist.testutil;

import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.Description;
import seedu.doist.model.task.Priority;
import seedu.doist.model.task.ReadOnlyTask;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Description desc;
    private Priority priority;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
        priority = new Priority();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.desc = taskToCopy.getDescription();
        this.priority = taskToCopy.getPriority();
        this.tags = taskToCopy.getTags();
    }

    public void setName(Description desc) {
        this.desc = desc;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
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
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getDescription().desc + " ");
        if (!this.getTags().isEmpty()) {
            sb.append("\\under");
            this.getTags().asObservableList().stream().forEach(s -> sb.append(" " + s.tagName));
        }
        return sb.toString();
    }
}
