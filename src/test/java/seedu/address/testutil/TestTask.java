package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Content;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskDateTime;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Content content;
    private TaskDateTime dateTime;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.content = taskToCopy.getContent();
        this.tags = taskToCopy.getTags();
    }

    public void setContent(Content name) {
        this.content = name;
    }

    public void setTaskDateTime(TaskDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    @Override
    public Content getContent() {
        return content;
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
        sb.append("add " + this.getContent().fullContent + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public TaskDateTime getDateTime() {
        return dateTime;
    }
}

